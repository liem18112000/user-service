package com.liem.userservice.dto.role;

import com.application.common.dto.BaseDto;
import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Role dto.
 *
 * @param <ID> the type parameter
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto<ID extends Serializable>
    extends BaseDto<ID> implements Serializable {

  /**
   * The Parent.
   */
  private RoleDto<Long> parent;

  /**
   * The Children.
   */
  private Set<RoleDto<Long>> children;

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = 4046249061656581699L;

  /**
   * The Auditable.
   */
  private boolean auditable;
}
