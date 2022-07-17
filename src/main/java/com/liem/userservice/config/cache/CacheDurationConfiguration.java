package com.liem.userservice.config.cache;

import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * The type Cache duration configuration.
 */
@Configuration
public class CacheDurationConfiguration {

  /**
   * The Users cache duration.
   */
  @Getter
  @Value("${spring.redis.cache-duration.users}")
  private Duration usersCacheDuration;

  /**
   * The Roles cache duration.
   */
  @Getter
  @Value("${spring.redis.cache-duration.roles}")
  private Duration rolesCacheDuration;

  /**
   * The Resources cache duration.
   */
  @Getter
  @Value("${spring.redis.cache-duration.resources}")
  private Duration resourcesCacheDuration;


}
