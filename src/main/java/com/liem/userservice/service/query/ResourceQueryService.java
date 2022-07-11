package com.liem.userservice.service.query;

import com.application.common.service.QueryService;
import com.liem.userservice.dto.resource.ResourceDto;
import com.liem.userservice.entity.ResourceEntity;
import java.util.List;

/**
 * The interface Resource query service.
 */
public interface ResourceQueryService
    extends QueryService<Long, ResourceEntity, ResourceDto<Long>> {

  /**
   * Gets all active resource.
   *
   * @return the all active resource
   */
  List<ResourceDto<Long>> getAllActiveResources();

}
