package com.liem.userservice.mapper;

import com.application.common.mapper.Mapper;
import com.application.common.mapper.impl.BaseMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liem.userservice.dto.role.RoleDto;
import com.liem.userservice.dto.user.UserDto;
import com.liem.userservice.entity.RoleEntity;
import com.liem.userservice.entity.UserEntity;
import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Role mapper.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RoleMapper
    implements Mapper<RoleEntity, RoleDto<Long>, Long>, Serializable {

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
   * Map to dto role dto.
   *
   * @param entity the entity
   * @return the role dto
   */
  @Override
  public RoleDto<Long> mapToDto(RoleEntity entity) {
    return this.objectMapper.convertValue(
        this.baseMapper.mapToDto(entity), new TypeReference<>() {});
  }

  /**
   * Map to entity role entity.
   *
   * @param dto the dto
   * @return the role entity
   */
  @Override
  public RoleEntity mapToEntity(RoleDto<Long> dto) {
    return this.objectMapper.convertValue(
        this.baseMapper.mapToEntity(dto), RoleEntity.class);
  }
}