package com.liem.userservice.service.query.impl;

import static com.application.common.service.util.ServiceUtil.validateNotNull;

import com.application.common.service.impl.CacheBasedQueryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liem.userservice.config.cache.CacheDurationConfiguration;
import com.liem.userservice.dto.user.UserDto;
import com.liem.userservice.entity.UserEntity;
import com.liem.userservice.mapper.UserMapper;
import com.liem.userservice.repository.UserRepository;
import com.application.common.service.CacheService;
import com.liem.userservice.service.query.UserQueryService;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type User query service.
 */
@Slf4j
@Service
@Transactional(readOnly = true)
public class UserQueryServiceImpl
    extends CacheBasedQueryServiceImpl<
            Long, UserEntity, UserDto<Long>,
            UserMapper, UserRepository
            > implements UserQueryService {

  /**
   * The Cache duration configuration.
   */
  protected final CacheDurationConfiguration cacheDurationConfig;

  /**
   * Instantiates a new Cache based query service.
   *
   * @param userRepository      the repo
   * @param mapper              the mapper
   * @param cacheService        the cache service
   * @param objectMapper        the object mapper
   * @param cacheDurationConfig the cache duration config
   */
  public UserQueryServiceImpl(
      UserRepository userRepository,
      UserMapper mapper,
      CacheService<String, String> cacheService,
      ObjectMapper objectMapper,
      CacheDurationConfiguration cacheDurationConfig) {
    super(userRepository, mapper, cacheService, objectMapper);
    this.cacheDurationConfig = cacheDurationConfig;
  }

  /**
   * Gets active user by username.
   *
   * @param username the username
   * @return the active user by username
   */
  @Override
  public UserDto<Long> getActiveUserByUsername(
      final String username) {
    validateNotNull(username, "username");
    final var user = getByCached(username,
        this::getActiveUserByUsernameWithException,
        UserEntity.class, this.cacheDurationConfig.getUsersCacheDuration());
    return this.mapper.mapToDto(user);
  }

  /**
   * Gets active user by username without exception.
   *
   * @param username the username
   * @return the active user by username without exception
   */
  @Override
  public UserDto<Long> getActiveUserByUsernameWithoutException(String username) {
    if (!StringUtils.hasText(username)) {
      return null;
    }

    return this.repo
        .findByUsernameAndIsActiveIsTrue(username)
        .map(this.mapper::mapToDto).orElse(null);
  }

  /**
   * Load user by username user details.
   *
   * @param username the username
   * @return the user details
   * @throws UsernameNotFoundException the username not found exception
   */
  @Override
  public UserDetails loadUserByUsername(
      final String username) throws UsernameNotFoundException {
    if (!StringUtils.hasText(username)) {
      throw new UsernameNotFoundException("Username is null or empty");
    }
    final var user = getByCached(username,
        this::getActiveUserByUsernameWithException,
        UserEntity.class, this.cacheDurationConfig.getUsersCacheDuration());
    return new User(user.getUsername(), user.getPassword(), user.isActive(),
        !user.isExpired(), !user.isExpired(), !user.isLocked(),
        user.getGrantedAuthorities());
  }

  /**
   * Gets active user by username with exception.
   *
   * @param username the username
   * @return the active user by username with exception
   */
  private UserEntity getActiveUserByUsernameWithException(
      final String username) {
    if (!StringUtils.hasText(username)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          String.format("User is not found by username: %s", username));
    }
    return this.repo.findByUsernameAndIsActiveIsTrue(username)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
            String.format("User is not found by username: %s", username)));
  }
}
