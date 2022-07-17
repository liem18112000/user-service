package com.liem.userservice.config.cache;

import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import java.nio.charset.Charset;
import java.time.Duration;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
