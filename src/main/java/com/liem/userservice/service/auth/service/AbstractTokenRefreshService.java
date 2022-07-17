package com.liem.userservice.service.auth.service;

import com.application.common.auth.service.TokenRefreshService;
import com.application.common.auth.token.TokenGenerator;
import com.liem.userservice.service.query.RefreshTokenQueryService;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Abstract token refresh service.
 *
 * @param <CREDENTIAL> the type parameter
 * @param <TOKEN>      the type parameter
 */
@RequiredArgsConstructor(onConstructor_={@Autowired})
public abstract class AbstractTokenRefreshService
    <CREDENTIAL extends Serializable, TOKEN extends Serializable>
    implements TokenRefreshService<CREDENTIAL, TOKEN> {

  /**
   * The Refresh token query service.
   */
  protected final RefreshTokenQueryService refreshTokenQueryService;

  /**
   * The Token generator.
   */
  protected final TokenGenerator<CREDENTIAL, TOKEN> tokenGenerator;

}
