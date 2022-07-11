package com.liem.userservice.service.cache;

import java.time.Duration;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * The type Redis cache service.
 */
@Service
public class StringRedisCacheService extends
    AbstractRedisCacheService<String, String> {

  /**
   * Instantiates a new Redis cache service.
   *
   * @param template the template
   */
  public StringRedisCacheService(
      RedisTemplate<String, String> template) {
    super(template);
  }

  /**
   * Get value.
   *
   * @param key the key
   * @return the value
   */
  @Override
  public String get(
      final @NotNull @NotEmpty String key) {
    return this.template.opsForValue().get(key);
  }

  /**
   * Cache value.
   *
   * @param key   the key
   * @param value the value
   * @return the value
   */
  @Override
  public String cache(
      final @NotNull @NotEmpty String key,
      final @NotNull @NotEmpty String value) {
    this.template.opsForValue().set(key, value);
    return value;
  }

  /**
   * Cache value.
   *
   * @param key      the key
   * @param value    the value
   * @param duration the duration
   * @return the value
   */
  @Override
  public String cache(
      final @NotNull @NotEmpty String key,
      final @NotNull @NotEmpty String value,
      final @NotNull Duration duration) {
    this.template.opsForValue().set(key, value, duration);
    return value;
  }
}
