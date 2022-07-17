package com.liem.userservice.service.auth.token;

import com.application.common.auth.token.TokenGenerator;
import com.liem.userservice.dto.token.RefreshTokenDto;
import java.util.UUID;
import org.springframework.stereotype.Service;

/**
 * The type Refresh token generator.
 */
@Service
public class RefreshTokenGenerator
    implements TokenGenerator<RefreshTokenDto<?>, String> {

  /**
   * Generate token token.
   *
   * @param refreshTokenDto the subject
   * @return the token
   */
  @Override
  public String generateToken(RefreshTokenDto<?> refreshTokenDto) {
    final var user = refreshTokenDto.getUser();
    final var username = user.getUsername();
    return String.format("%s@%s", username, UUID.randomUUID());
  }
}
