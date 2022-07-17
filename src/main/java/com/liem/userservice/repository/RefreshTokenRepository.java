package com.liem.userservice.repository;

import com.application.common.repository.BaseRepository;
import com.liem.userservice.dto.auth.Token;
import com.liem.userservice.dto.resource.ResourceProjection;
import com.liem.userservice.entity.RefreshTokenEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * The interface Resource repository.
 */
@Repository
@RepositoryRestResource(
    path = "refresh-tokens",
    collectionResourceRel = "refresh-tokens",
    itemResourceRel = "refresh-token",
    excerptProjection = ResourceProjection.class)
public interface RefreshTokenRepository
    extends BaseRepository<RefreshTokenEntity, Long> {

  /**
   * Gets refresh token by username.
   *
   * @param username the username
   * @return the refresh token by username
   */
  @Query(value = " SELECT token "
      + " FROM refresh_token rt, user u "
      + " WHERE u.username = :username "
      + " AND rt.is_active = b'1' "
      + " AND u.is_active = b'1' "
      + " AND u.id = rt.user_id "
      , nativeQuery = true)
  Optional<Token> getRefreshTokenByUsername(String username);

  /**
   * Find by token and is active is true optional.
   *
   * @param token the token
   * @return the refresh token by unique token
   */
  Optional<RefreshTokenEntity> findByTokenAndIsActiveIsTrue(String token);
}
