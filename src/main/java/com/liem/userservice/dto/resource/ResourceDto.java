package com.liem.userservice.dto.resource;

import com.application.common.dto.BaseDto;
import com.liem.userservice.dto.role.RoleDto;
import com.liem.userservice.entity.enums.ResourceRoleType;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Resource dto.
 *
 * @param <ID> the type parameter
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDto<ID extends Serializable>
    extends BaseDto<ID> implements Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = -6354210347385396745L;

  /**
   * The Path pattern.
   */
  private String pathPattern;

  /**
   * The Resource role type.
   */
  private ResourceRoleType resourceRoleType;

  /**
   * The Roles.
   */
  private Set<RoleDto<ID>> roles;
}
