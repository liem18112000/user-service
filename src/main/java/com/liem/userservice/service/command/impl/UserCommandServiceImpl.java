package com.liem.userservice.service.command.impl;

import static com.application.common.service.util.ServiceUtil.throwExceptionForNotFoundEntityById;
import static com.application.common.service.util.ServiceUtil.validateNotNull;

import com.application.common.service.impl.BaseCommandService;
import com.liem.userservice.dto.token.RefreshTokenDto;
import com.liem.userservice.dto.user.UserDto;
import com.liem.userservice.entity.UserEntity;
import com.liem.userservice.mapper.UserMapper;
import com.liem.userservice.repository.UserRepository;
import com.liem.userservice.service.command.RefreshTokenCommandService;
import com.liem.userservice.service.command.UserCommandService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.rest.core.event.AfterCreateEvent;
import org.springframework.data.rest.core.event.AfterSaveEvent;
import org.springframework.data.rest.core.event.BeforeCreateEvent;
import org.springframework.data.rest.core.event.BeforeSaveEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type User command service.
 */
@Service
public class UserCommandServiceImpl
    extends BaseCommandService<
        Long, UserEntity, UserDto<Long>, UserMapper, UserRepository
        > implements UserCommandService {

  /**
   * The Password encoder.
   */
  protected final PasswordEncoder passwordEncoder;

  /**
   * The Token command service.
   */
  protected final RefreshTokenCommandService tokenCommandService;

  /**
   * Instantiates a new User command service.
   *
   * @param userRepository      the user repository
   * @param mapper              the mapper
   * @param publisher           the publisher
   * @param passwordEncoder     the password encoder
   * @param tokenCommandService the token command service
   */
  public UserCommandServiceImpl(
      UserRepository userRepository,
      UserMapper mapper,
      ApplicationEventPublisher publisher,
      PasswordEncoder passwordEncoder,
      RefreshTokenCommandService tokenCommandService) {
    super(userRepository, mapper, publisher);
    this.passwordEncoder = passwordEncoder;
    this.tokenCommandService = tokenCommandService;
  }

  /**
   * Create dto.
   *
   * @param dto the dto
   * @return the dto
   */
  @Override
  @Transactional
  public UserDto<Long> create(UserDto<Long> dto) {
    validateNotNull(dto, "DTO");
    var entityToCreate = this.mapper.mapToEntity(dto);
    entityToCreate.setPassword(passwordEncoder.encode(entityToCreate.getPassword()));
    publisher.publishEvent(new BeforeCreateEvent(entityToCreate));
    final var createdEntity = this.repo.save(entityToCreate);
    publisher.publishEvent(new AfterCreateEvent(createdEntity));
    final var tokenDto = new RefreshTokenDto<Long>();
    tokenDto.setUser(this.mapper.mapToDto(createdEntity));
    final var tokenToCreate = this.tokenCommandService.create(tokenDto);
    publisher.publishEvent(new AfterCreateEvent(tokenToCreate));
    return this.mapper.mapToDto(createdEntity);
  }

  /**
   * Deactivate user dto.
   *
   * @param id the id
   * @return the user dto
   */
  @Override
  public UserDto<Long> deactivate(final Long id) {
    final var entity = getEntityById(id);
    final var entityToDeactivate = entity.<UserEntity>deactivate();
    publishBeforeUpdateEvent(entityToDeactivate);
    final var updatedEntity = this.repo.save(entityToDeactivate);
    publishAfterUpdateEvent(updatedEntity);
    return this.mapper.mapToDto(updatedEntity);
  }

  /**
   * Activate user dto.
   *
   * @param id the id
   * @return the user dto
   */
  @Override
  public UserDto<Long> activate(final Long id) {
    final var entity = getEntityById(id);
    final var entityToActivate = entity.<UserEntity>activate();
    publishBeforeUpdateEvent(entityToActivate);
    final var updatedEntity = this.repo.save(entityToActivate);
    publishAfterUpdateEvent(updatedEntity);
    return this.mapper.mapToDto(updatedEntity);
  }

  /**
   * Gets entity by id.
   *
   * @param entityId the entity id
   * @return the entity by id
   */
  private UserEntity getEntityById(Long entityId) {
    validateNotNull(entityId, "entityId");
    return this.repo.findById(entityId).orElseThrow(
        throwExceptionForNotFoundEntityById(entityId));
  }

  /**
   * Publish after update event.
   *
   * @param updatedEntity the updated entity
   */
  private void publishAfterUpdateEvent(UserEntity updatedEntity) {
    this.publisher.publishEvent(new AfterSaveEvent(updatedEntity));
  }

  /**
   * Publish before update event.
   *
   * @param entityToUpdate the entity to update
   */
  private void publishBeforeUpdateEvent(UserEntity entityToUpdate) {
    this.publisher.publishEvent(new BeforeSaveEvent(entityToUpdate));
  }
}
