package com.liem.userservice.service.auth.service;

import com.application.common.auth.token.TokenGenerator;
import com.liem.userservice.dto.user.UserDto;
import com.liem.userservice.service.query.RefreshTokenQueryService;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Token refresh service.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class TokenRefreshServiceImpl
    extends AbstractTokenRefreshService<UserDto<Long>, String> {

  /**
   * Instantiates a new Token refresh service.
   *
   * @param refreshTokenQueryService the refresh token query service
   * @param tokenGenerator           the token generator
   */
  public TokenRefreshServiceImpl(
      RefreshTokenQueryService refreshTokenQueryService,
      TokenGenerator<UserDto<Long>, String> tokenGenerator) {
    super(refreshTokenQueryService, tokenGenerator);
  }

  /**
   * Refresh token.
   *
   * @param userDto the refresh token
   * @return the token
   */
  @Override
  public String refreshToken(
      final @NotNull UserDto<Long> userDto) {
    final var refreshToken = userDto.getRefreshToken();
    final var validToken= this.refreshTokenQueryService
        .getActiveTokenByToken(refreshToken);
    final var validUser = validToken.getUser();
    return this.tokenGenerator.generateToken(validUser);
  }
}
