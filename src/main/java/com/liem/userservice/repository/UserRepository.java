package com.liem.userservice.repository;

import com.application.common.repository.BaseRepository;
import com.liem.userservice.dto.user.UserProjection;
import com.liem.userservice.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
@RepositoryRestResource(
    path = "users",
    collectionResourceRel = "users",
    itemResourceRel = "user",
    excerptProjection = UserProjection.class)
public interface UserRepository
    extends BaseRepository<UserEntity, Long> {

  /**
   * Find by username and active is true optional.
   *
   * @param username the username
   * @return the optional
   */
  Optional<UserEntity> findByUsernameAndIsActiveIsTrue(String username);
}
