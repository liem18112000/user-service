package com.liem.userservice.dto.resource;

import com.application.common.dto.BaseProjection;
import com.liem.userservice.entity.ResourceEntity;
import com.liem.userservice.entity.RoleEntity;
import com.liem.userservice.entity.enums.ResourceRoleType;
import java.util.Set;
import org.springframework.data.rest.core.config.Projection;

/**
 * The interface Resource projection.
 */
@Projection(types = {ResourceEntity.class})
public interface ResourceProjection extends BaseProjection<Long> {

  /**
   * Gets id.
   *
   * @return the id
   */
  Long getId();

  /**
   * The Path pattern.
   *
   * @return the path pattern
   */
  String getPathPattern();

  /**
   * The Resource role type.
   *
   * @return the resource role type
   */
  ResourceRoleType getResourceRoleType();

  /**
   * The Roles.
   *
   * @return the roles
   */
  Set<BaseProjection<Long>> getRoles();

}
