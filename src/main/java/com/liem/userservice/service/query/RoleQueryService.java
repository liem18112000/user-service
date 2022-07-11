package com.liem.userservice.service.query;

import com.application.common.service.QueryService;
import com.liem.userservice.dto.role.RoleDto;
import com.liem.userservice.entity.RoleEntity;

/**
 * The interface Role query service.
 */
public interface RoleQueryService
    extends QueryService<Long, RoleEntity, RoleDto<Long>> {

}
