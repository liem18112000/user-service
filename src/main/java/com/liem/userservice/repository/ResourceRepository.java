package com.liem.userservice.repository;

import com.application.common.repository.BaseRepository;
import com.liem.userservice.dto.resource.ResourceProjection;
import com.liem.userservice.entity.ResourceEntity;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * The interface Resource repository.
 */
@Repository
@RepositoryRestResource(
    path = "resources",
    collectionResourceRel = "resources",
    itemResourceRel = "resource",
    excerptProjection = ResourceProjection.class)
public interface ResourceRepository
    extends BaseRepository<ResourceEntity, Long> {
}
