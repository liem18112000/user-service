package com.liem.userservice.service.command;

import com.application.common.service.CommandService;
import com.liem.userservice.dto.resource.ResourceDto;

/**
 * The interface Resource command service.
 */
public interface ResourceCommandService
    extends CommandService<Long, ResourceDto<Long>> {

}
