package com.liem.userservice.config.security;

import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RefreshTokenConfiguration {

  /**
   * The Token secret.
   */
  @Getter
  @Value("${spring.refresh-token.secret}")
  private String tokenSecret;

  /**
   * The Token expiration.
   */
  @Getter
  @Value("${spring.refresh-token.expiration}")
  private Duration tokenExpiration;

  /**
   * The Token cache duration.
   */
  @Getter
  @Value("${spring.refresh-token.cache-duration}")
  private Duration tokenCacheDuration;

}
