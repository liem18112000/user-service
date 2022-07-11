package com.liem.userservice.config.cache;

import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheDurationConfiguration {

  @Getter
  @Value("${spring.redis.cache-duration.users}")
  private Duration usersCacheDuration;

  @Getter
  @Value("${spring.redis.cache-duration.roles}")
  private Duration rolesCacheDuration;

  @Getter
  @Value("${spring.redis.cache-duration.resources}")
  private Duration resourcesCacheDuration;

}
