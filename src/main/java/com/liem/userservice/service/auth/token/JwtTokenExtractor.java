package com.liem.userservice.service.auth.token;

import com.liem.userservice.config.security.JwtConfiguration;
import com.application.common.auth.token.TokenExtractor;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * The type Jwt token extractor.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class JwtTokenExtractor implements TokenExtractor<String> {

  /**
   * The Configuration.
   */
  private final JwtConfiguration configuration;

  /**
   * Gets user from token.
   *
   * @param parsedToken the parsed token
   * @return the user from token
   */
  @Override
  public String getUserFromToken(final String parsedToken) {
    if (!StringUtils.hasText(parsedToken)) {
      throw new IllegalArgumentException("Parsed token is null or empty");
    }
    final var jwtSecret = this.configuration.getTokenSecret();
    return Jwts.parser().setSigningKey(jwtSecret)
        .parseClaimsJws(parsedToken).getBody().getSubject();
  }
}
