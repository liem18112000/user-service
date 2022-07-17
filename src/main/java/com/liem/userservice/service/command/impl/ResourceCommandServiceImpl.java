package com.liem.userservice.service.command.impl;

import com.application.common.service.impl.BaseCommandService;
import com.liem.userservice.dto.resource.ResourceDto;
import com.liem.userservice.entity.ResourceEntity;
import com.liem.userservice.mapper.ResourceMapper;
import com.liem.userservice.repository.ResourceRepository;
import com.liem.userservice.service.command.ResourceCommandService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * The type Resource command service.
 */
@Service
public class ResourceCommandServiceImpl
    extends BaseCommandService<
    Long, ResourceEntity, ResourceDto<Long>,
    ResourceMapper, ResourceRepository
    > implements ResourceCommandService {

  /**
   * Instantiates a new Resource command service.
   *
   * @param resourceRepository the resource repository
   * @param mapper             the mapper
   * @param publisher          the publisher
   */
  public ResourceCommandServiceImpl(
      ResourceRepository resourceRepository,
      ResourceMapper mapper,
      ApplicationEventPublisher publisher
  ) {
    super(resourceRepository, mapper, publisher);
  }
}
