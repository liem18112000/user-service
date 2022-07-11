package com.liem.userservice.service.query.impl;

import com.application.common.entity.BaseEntity;
import com.application.common.service.CacheService;
import com.application.common.service.impl.CacheBasedQueryServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liem.userservice.config.cache.CacheDurationConfiguration;
import com.liem.userservice.dto.resource.ResourceDto;
import com.liem.userservice.entity.ResourceEntity;
import com.liem.userservice.mapper.ResourceMapper;
import com.liem.userservice.repository.ResourceRepository;
import com.liem.userservice.service.query.ResourceQueryService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * The type Resource query service.
 */
@Service
public class ResourceQueryServiceImpl
    extends CacheBasedQueryServiceImpl<
        Long, ResourceEntity, ResourceDto<Long>,
        ResourceMapper, ResourceRepository
        > implements ResourceQueryService {

  /**
   * The Cache duration configuration.
   */
  protected final CacheDurationConfiguration cacheDurationConfig;

  /**
   * Instantiates a new Cache based query service.
   *
   * @param resourceRepository         the repo
   * @param mapper                     the mapper
   * @param cacheService               the cache service
   * @param objectMapper               the object mapper
   * @param cacheDurationConfiguration the cache duration configuration
   */
  public ResourceQueryServiceImpl(
      ResourceRepository resourceRepository,
      ResourceMapper mapper,
      CacheService<String, String> cacheService,
      ObjectMapper objectMapper,
      CacheDurationConfiguration cacheDurationConfiguration) {
    super(resourceRepository, mapper, cacheService, objectMapper);
    this.cacheDurationConfig = cacheDurationConfiguration;
  }

  /**
   * Gets all active resources.
   *
   * @return the all active resources
   */
  @Override
  public List<ResourceDto<Long>> getAllActiveResources() {
    return this.getAllByCached(
        "AllActiveResources", i -> this.repo.findAll().stream()
            .filter(BaseEntity::isActive).map(this.mapper::mapToDto)
            .collect(Collectors.toList()),
        new TypeReference<>() {}, cacheDurationConfig.getResourcesCacheDuration());
  }
}
