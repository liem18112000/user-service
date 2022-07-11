package com.liem.userservice.service.auth.token;

import com.application.common.dto.BaseDto;
import com.liem.userservice.config.security.JwtConfiguration;
import com.liem.userservice.dto.user.UserDto;
import com.application.common.auth.token.TokenGenerator;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Jwt token generator.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class JwtTokenGenerator
    implements TokenGenerator<UserDto<Long>, String> {

  /**
   * The Configuration.
   */
  private final JwtConfiguration configuration;

  @Override
  public String generateToken(final UserDto<Long> userDto) {
    final var principal = userDto.getUsername();
    final var jwtSecret = this.configuration.getTokenSecret();
    final var authorities = userDto.getRoles().stream()
        .map(BaseDto::getName).collect(Collectors.toSet());
    final var issuedAt = new Date();
    return Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setSubject(principal)
        .claim("authorities", authorities)
        .setIssuedAt(issuedAt)
        .setExpiration(this.calculateExpiration(issuedAt))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  protected Date calculateExpiration(final Date from) {
    final var jwtExpiration = this.configuration.getTokenExpiration();
    return new Date(from.getTime() + jwtExpiration.toMillis());
  }
}
