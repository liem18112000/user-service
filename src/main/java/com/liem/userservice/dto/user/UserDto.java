package com.liem.userservice.dto.user;

import com.application.common.dto.BaseDto;
import com.liem.userservice.dto.role.RoleDto;
import java.io.Serializable;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * The type User dto.
 *
 * @param <ID> the type parameter
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto<ID extends Serializable>
    extends BaseDto<ID> implements Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = -140825052051292292L;

  /**
   * The Username.
   */
  @NotNull
  @NotEmpty
  @Length(min=8, max=255)
  private String username;

  /**
   * The Password.
   */
  @NotNull
  @NotEmpty
  @Length(min=8, max=255)
  private String password;

  /**
   * The Roles.
   */
  @NotNull
  @NotEmpty
  private Set<RoleDto<ID>> roles;

  /**
   * The Active.
   */
  private boolean active;

  /**
   * The Locked.
   */
  private boolean locked;

  /**
   * The Expired.
   */
  private boolean expired;

  /**
   * The Token.
   */
  private String token;
}
