package com.liem.userservice.service.auth.token;

import com.application.common.auth.token.TokenValidator;
import com.liem.userservice.dto.token.RefreshTokenDto;
import java.time.Instant;
import org.springframework.stereotype.Service;

/**
 * The type Refresh token validator.
 */
@Service
public class RefreshTokenValidator
    implements TokenValidator<RefreshTokenDto<?>> {

  /**
   * Validate boolean.
   *
   * @param parsedToken the parsed token
   * @return the boolean
   */
  @Override
  public boolean validate(RefreshTokenDto<?> parsedToken) {
    final var expiredAt = parsedToken.getExpiredAt();
    final var now = Instant.now();
    return expiredAt.isAfter(now);
  }
}
