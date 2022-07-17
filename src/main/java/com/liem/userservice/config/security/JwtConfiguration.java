package com.liem.userservice.config.security;

import com.application.common.service.CacheService;
import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Configuration;

/**
 * The type Jwt configuration.
 */
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Configuration
public class JwtConfiguration {

  /**
   * The constant AUTHORIZATION_HEADER.
   */
  public static final String AUTHORIZATION_HEADER = "Authorization";

  /**
   * The constant SPRING_JWT_WHITE_LIST.
   */
  public static final String SPRING_JWT_WHITE_LIST = "spring.jwt.white-list";

  /**
   * The constant SPRING_JWT_SECRET.
   */
  public static final String SPRING_JWT_SECRET = "spring.jwt.secret";

  /**
   * The Cache service.
   */
  private final CacheService<String, String> cacheService;

  /**
   * The Token secret.
   */
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
  @Value("${spring.jwt.white-list}")
  private String jwtWhiteList;

  /**
   * The Token cache duration.
   */
  @Getter
  @Value("${spring.jwt.cache-duration}")
  private Duration tokenCacheDuration;

  /**
   * Gets jwt white list.
   *
   * @return the jwt white list
   */
  public String getJwtWhiteList() {
    return this.cacheService.get(
        SPRING_JWT_WHITE_LIST,
        () -> this.jwtWhiteList,
        Duration.ofMinutes(60)
    );
  }

  /**
   * Gets token secret.
   *
   * @return the token secret
   */
  public String getTokenSecret() {
    return this.cacheService.get(
        SPRING_JWT_SECRET,
        () -> this.tokenSecret,
        Duration.ofMinutes(60)
    );
  }
}
