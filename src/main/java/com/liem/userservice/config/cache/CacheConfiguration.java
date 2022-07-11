package com.liem.userservice.config.cache;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * The type Cache configuration.
 */
@Configuration
public class CacheConfiguration {

  @Getter
  @Value("${spring.redis.host}")
  private String host;

  @Getter
  @Value("${spring.redis.port}")
  private String port;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    configuration.setHostName(host);
    configuration.setPort(Integer.parseInt(port));
    return new LettuceConnectionFactory(configuration);
  }

}
