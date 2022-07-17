package com.liem.userservice.service.query;

import com.application.common.service.QueryService;
import com.liem.userservice.dto.token.RefreshTokenDto;
import com.liem.userservice.dto.user.UserDto;
import com.liem.userservice.entity.RefreshTokenEntity;

/**
 * The interface Refresh token query service.
 */
public interface RefreshTokenQueryService extends
    QueryService<Long, RefreshTokenEntity, RefreshTokenDto<Long>> {

  /**
   * Gets active token by username.
   *
   * @param username Username
   * @return the active token by user
   */
  String getActiveTokenByUsername(String username);

  /**
   * Gets active token by token.
   *
   * @param token the token
   * @return the active token by token
   */
  RefreshTokenDto<Long> getActiveTokenByToken(String token);
}
