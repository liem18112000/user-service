package com.liem.userservice.service.query.impl;

import com.application.common.service.CacheService;
import com.application.common.service.impl.CacheBasedQueryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liem.userservice.dto.token.RefreshTokenDto;
import com.liem.userservice.entity.RefreshTokenEntity;
import com.liem.userservice.mapper.RefreshTokenMapper;
import com.liem.userservice.repository.RefreshTokenRepository;
import com.liem.userservice.service.query.RefreshTokenQueryService;
import java.time.Duration;
import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Refresh token query service.
 */
@Service
@Transactional(readOnly = true)
public class RefreshTokenQueryServiceImpl
    extends CacheBasedQueryServiceImpl<
            Long, RefreshTokenEntity, RefreshTokenDto<Long>,
            RefreshTokenMapper, RefreshTokenRepository
            > implements RefreshTokenQueryService {

  /**
   * Instantiates a new Cache based query service.
   *
   * @param refreshTokenRepository the repo
   * @param mapper                 the mapper
   * @param cacheService           the cache service
   * @param objectMapper           the object mapper
   */
  public RefreshTokenQueryServiceImpl(
      RefreshTokenRepository refreshTokenRepository,
      RefreshTokenMapper mapper,
      CacheService<String, String> cacheService,
      ObjectMapper objectMapper) {
    super(refreshTokenRepository, mapper, cacheService, objectMapper);
  }

  /**
   * Gets active token by user.
   *
   * @param username the username
   * @return the active token by user
   */
  @Override
  public String getActiveTokenByUsername(
      final @NotNull String username) {
    final var key = String.format("%s-%s", "ActiveRefreshTokenByUsername", username);
    return this.getByCached(key, k -> this.repo.getRefreshTokenByUsername(username)
        .orElseThrow(() -> new EntityNotFoundException(String.format(
            "Refresh token not found by username '%s'", username))).getToken(),
        String.class, Duration.ofMinutes(60));
  }

  /**
   * Gets active token by token.
   *
   * @param token the token
   * @return the active token by token
   */
  @Override
  public RefreshTokenDto<Long> getActiveTokenByToken(
      final @NotNull String token) {
    return this.mapper.mapToDto(this.repo.findByTokenAndIsActiveIsTrue(token)
        .orElseThrow(() -> new EntityNotFoundException(String.format(
            "Refresh token not found by token '%s'", token))));
  }
}
