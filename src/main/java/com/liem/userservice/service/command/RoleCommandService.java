package com.liem.userservice.service.command;

import com.application.common.service.CommandService;
import com.liem.userservice.dto.role.RoleDto;

/**
 * The interface Role command service.
 */
public interface RoleCommandService
    extends CommandService<Long, RoleDto<Long>> {

}
