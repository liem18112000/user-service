package com.liem.userservice.repository;

import com.application.common.repository.BaseRepository;
import com.liem.userservice.dto.role.RoleProjection;
import com.liem.userservice.dto.user.UserProjection;
import com.liem.userservice.entity.RoleEntity;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * The interface Role repository.
 */
@Repository
@RepositoryRestResource(
    path = "roles",
    collectionResourceRel = "roles",
    itemResourceRel = "role",
    excerptProjection = RoleProjection.class)
public interface RoleRepository
    extends BaseRepository<RoleEntity, Long> {
}
