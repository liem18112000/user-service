package com.liem.userservice.service.command;

import com.application.common.service.CommandService;
import com.liem.userservice.dto.user.UserDto;

/**
 * The interface User command service.
 */
public interface UserCommandService
    extends CommandService<Long, UserDto<Long>> {

  /**
   * Deactivate user dto.
   *
   * @param id the id
   * @return the user dto
   */
  UserDto<Long> deactivate(Long id);

  /**
   * Activate user dto.
   *
   * @param id the id
   * @return the user dto
   */
  UserDto<Long> activate(Long id);
}
