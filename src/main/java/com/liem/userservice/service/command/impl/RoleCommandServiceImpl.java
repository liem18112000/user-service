package com.liem.userservice.service.command.impl;

import com.application.common.service.impl.BaseCommandService;
import com.liem.userservice.dto.role.RoleDto;
import com.liem.userservice.entity.RoleEntity;
import com.liem.userservice.mapper.RoleMapper;
import com.liem.userservice.repository.RoleRepository;
import com.liem.userservice.service.command.RoleCommandService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * The type Role command service.
 */
@Service
public class RoleCommandServiceImpl
    extends BaseCommandService<
    Long, RoleEntity, RoleDto<Long>, RoleMapper, RoleRepository
    > implements RoleCommandService {

  /**
   * Instantiates a new Role command service.
   *
   * @param roleRepository the role repository
   * @param mapper         the mapper
   * @param publisher      the publisher
   */
  public RoleCommandServiceImpl(RoleRepository roleRepository,
      RoleMapper mapper, ApplicationEventPublisher publisher) {
    super(roleRepository, mapper, publisher);
  }
}
