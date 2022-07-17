package com.liem.userservice.mapper;

import com.application.common.mapper.Mapper;
import com.application.common.mapper.impl.BaseMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liem.userservice.dto.token.RefreshTokenDto;
import com.liem.userservice.entity.RefreshTokenEntity;
import java.io.Serializable;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Resource mapper.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RefreshTokenMapper
    implements Mapper<RefreshTokenEntity, RefreshTokenDto<Long>, Long>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = -8572601467307225606L;

  /**
   * The Base mapper.
   */
  private final BaseMapper<Long> baseMapper;

  /**
   * The User mapper.
   */
  private final UserMapper userMapper;

  /**
   * The Object mapper.
   */
  private final ObjectMapper objectMapper;

  /**
   * Map to dto role dto.
   *
   * @param entity the entity
   * @return the role dto
   */
  @Override
  public RefreshTokenDto<Long> mapToDto(RefreshTokenEntity entity) {
    final var dto = this.objectMapper.convertValue(
        this.baseMapper.mapToDto(entity), new TypeReference<RefreshTokenDto<Long>>() {});
    Objects.requireNonNull(entity.getUser(), "User is null");
    dto.setToken(entity.getToken());
    dto.setExpiredAt(entity.getExpiredAt());
    dto.setUser(this.userMapper.mapToDto(entity.getUser()));
    return dto;
  }

  /**
   * Map to entity role entity.
   *
   * @param dto the dto
   * @return the role entity
   */
  @Override
  public RefreshTokenEntity mapToEntity(RefreshTokenDto<Long> dto) {
    final var entity = this.objectMapper.convertValue(
        this.baseMapper.mapToEntity(dto), RefreshTokenEntity.class);
    Objects.requireNonNull(dto.getUser(), "User is null");
    entity.setToken(dto.getToken());
    entity.setExpiredAt(dto.getExpiredAt());
    entity.setUser(this.userMapper.mapToEntity(dto.getUser()));
    return entity;
  }
}