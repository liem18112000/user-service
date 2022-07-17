package com.liem.userservice.dto.token;

import com.application.common.dto.BaseDto;
import com.liem.userservice.dto.user.UserDto;
import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The type Refresh token dto.
 *
 * @param <ID> the type parameter
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDto <ID extends Serializable>
    extends BaseDto<ID> implements Serializable {

  /**
   * The constant serialVersionUID.
   */
  private static final long serialVersionUID = -3923751678354631436L;

  /**
   * The User.
   */
  private UserDto<ID> user;

  /**
   * The Token.
   */
  private String token;

  /**
   * The Expired at.
   */
  private Instant expiredAt;
}
