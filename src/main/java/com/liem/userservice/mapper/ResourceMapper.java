package com.liem.userservice.mapper;

import com.application.common.mapper.Mapper;
import com.application.common.mapper.impl.BaseMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liem.userservice.dto.resource.ResourceDto;
import com.liem.userservice.dto.role.RoleDto;
import com.liem.userservice.entity.ResourceEntity;
import com.liem.userservice.entity.RoleEntity;
import java.io.Serializable;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The type Resource mapper.
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class ResourceMapper
    implements Mapper<ResourceEntity, ResourceDto<Long>, Long>, Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 5573292144600681323L;

  /**
   * The Base mapper.
   */
  private final BaseMapper<Long> baseMapper;

  /**
   * The Role mapper.
   */
  private final RoleMapper roleMapper;

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
  public ResourceDto<Long> mapToDto(ResourceEntity entity) {
    final var dto = this.objectMapper.convertValue(
        this.baseMapper.mapToDto(entity), new TypeReference<ResourceDto<Long>>() {});
    final var roles = entity.getRoles();
    if (roles != null) {
      dto.setRoles(roles.stream().map(this.roleMapper::mapToDto)
          .collect(Collectors.toSet()));
    }
    dto.setResourceRoleType(entity.getResourceRoleType());
    dto.setPathPattern(entity.getPathPattern());
    return dto;
  }

  /**
   * Map to entity role entity.
   *
   * @param dto the dto
   * @return the role entity
   */
  @Override
  public ResourceEntity mapToEntity(ResourceDto<Long> dto) {
    final var entity = this.objectMapper.convertValue(
        this.baseMapper.mapToEntity(dto), ResourceEntity.class);
    final var roles = dto.getRoles();
    if (roles != null) {
      entity.setRoles(roles.stream().map(this.roleMapper::mapToEntity)
          .collect(Collectors.toSet()));
    }
    entity.setResourceRoleType(dto.getResourceRoleType());
    entity.setPathPattern(dto.getPathPattern());
    return entity;
  }
}