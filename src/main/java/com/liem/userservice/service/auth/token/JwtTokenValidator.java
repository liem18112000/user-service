package com.liem.userservice.service.auth.token;

import com.liem.userservice.config.security.JwtConfiguration;
import com.application.common.auth.token.TokenValidator;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Jwt token validator.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class JwtTokenValidator implements TokenValidator<String> {

  /**
   * The Configuration.
   */
  private final JwtConfiguration configuration;

  /**
   * Validate boolean.
   *
   * @param parsedToken the parsed token
   * @return the boolean
   */
  @Override
  public boolean validate(String parsedToken) {
    final var jwtSecret = configuration.getTokenSecret();
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(parsedToken);
      return true;
    } catch (SignatureException e) {
      final var message = String.format("Invalid JWT signature: %s", e.getMessage());
      log.error(message);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
    } catch (MalformedJwtException e) {
      final var message = String.format("Invalid JWT token: {}: %s", e.getMessage());
      log.error(message);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
    } catch (ExpiredJwtException e) {
      final var message = String.format("JWT token is expired: %s", e.getMessage());
      log.error(message);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
    } catch (UnsupportedJwtException e) {
      final var message = String.format("JWT token is unsupported: %s", e.getMessage());
      log.error(message);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
    } catch (IllegalArgumentException e) {
      final var message = String.format("JWT claims string is empty: %s", e.getMessage());
      log.error(message);
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, message);
    }
  }
}
