package com.liem.userservice.service.command;

import com.application.common.service.CommandService;
import com.liem.userservice.dto.token.RefreshTokenDto;

/**
 * The interface Refresh token command service.
 */
public interface RefreshTokenCommandService
    extends CommandService<Long, RefreshTokenDto<Long>> {

}
