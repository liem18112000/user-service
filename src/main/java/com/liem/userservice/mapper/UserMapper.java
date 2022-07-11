package com.liem.userservice.mapper;

import com.application.common.mapper.Mapper;
import com.application.common.mapper.impl.BaseMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liem.userservice.dto.user.UserDto;
import com.liem.userservice.entity.UserEntity;
import java.io.Serializable;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * The type User mapper.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserMapper
    implements Mapper<UserEntity, UserDto<Long>, Long>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 6147164258318707053L;

  /**
   * The Base mapper.
   */
  private final BaseMapper<Long> baseMapper;

  /**
   * The Object mapper.
   */
  private final ObjectMapper objectMapper;

  /**
   * The Role mapper.
   */
  private final RoleMapper roleMapper;


  /**
   * Map to dto user dto.
   *
   * @param entity the entity
   * @return the user dto
   */
  @Override
  public UserDto<Long> mapToDto(UserEntity entity) {
    final UserDto<Long> dto = this.objectMapper.convertValue(
        this.baseMapper.mapToDto(entity), new TypeReference<>() {});
    dto.setUsername(entity.getUsername());
    dto.setPassword(entity.getPassword());
    dto.setRoles(Optional.ofNullable(entity.getRoles())
        .map(roles -> roles.stream().map(this.roleMapper::mapToDto)
            .collect(Collectors.toSet()))
        .orElse(Set.of()));
    dto.setActive(entity.isActive());
    dto.setExpired(entity.isExpired());
    dto.setLocked(entity.isLocked());
    return dto;
  }

  /**
   * Map to entity user entity.
   *
   * @param dto the dto
   * @return the user entity
   */
  @Override
  public UserEntity mapToEntity(UserDto<Long> dto) {
    final UserEntity entity = this.objectMapper.convertValue(
        this.baseMapper.mapToEntity(dto), UserEntity.class);
    entity.setUsername(dto.getUsername());
    entity.setPassword(dto.getPassword());
    final var roles = dto.getRoles();
    if (roles != null) {
      entity.setRoles(dto.getRoles().stream()
          .map(this.roleMapper::mapToEntity)
          .collect(Collectors.toSet()));
    }
    entity.setActive(dto.isActive());
    entity.setExpired(dto.isExpired());
    entity.setLocked(dto.isLocked());
    return entity;
  }
}