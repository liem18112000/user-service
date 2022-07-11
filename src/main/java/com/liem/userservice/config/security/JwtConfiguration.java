package com.liem.userservice.config.security;

import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The type Jwt configuration.
 */
@Configuration
public class JwtConfiguration {

  /**
   * The constant AUTHORIZATION_HEADER.
   */
  public static final String AUTHORIZATION_HEADER = "Authorization";

  /**
   * The Token secret.
   */
  @Getter
  @Value("${spring.jwt.secret}")
  private String tokenSecret;

  /**
   * The Token expiration.
   */
  @Getter
  @Value("${spring.jwt.expiration}")
  private Duration tokenExpiration;

  /**
   * The Jwt white list.
   */
  @Getter
  @Value("${spring.jwt.white-list}")
  private String jwtWhiteList;

  /**
   * The Token cache duration.
   */
  @Getter
  @Value("${spring.jwt.cache-duration}")
  private Duration tokenCacheDuration;
}
