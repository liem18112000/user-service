package com.liem.userservice.service.cache;

import com.application.common.service.CacheService;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * The type Abstract redis cache service.
 *
 * @param <KEY>   the type parameter
 * @param <VALUE> the type parameter
 */
@RequiredArgsConstructor(onConstructor_={@Autowired})
public abstract class AbstractRedisCacheService
    <KEY extends Serializable, VALUE>
    implements CacheService<KEY, VALUE> {

  /**
   * The Template.
   */
  protected final RedisTemplate<KEY, VALUE> template;
}
