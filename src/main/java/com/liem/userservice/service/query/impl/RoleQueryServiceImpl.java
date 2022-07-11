package com.liem.userservice.service.query.impl;

import com.application.common.service.impl.BaseQueryService;
import com.liem.userservice.dto.role.RoleDto;
import com.liem.userservice.entity.RoleEntity;
import com.liem.userservice.mapper.RoleMapper;
import com.liem.userservice.repository.RoleRepository;
import com.liem.userservice.service.query.RoleQueryService;
import org.springframework.stereotype.Service;

/**
 * The type User query service.
 */
@Service
public class RoleQueryServiceImpl
    extends BaseQueryService<
        Long, RoleEntity, RoleDto<Long>,
        RoleMapper, RoleRepository
        > implements RoleQueryService {

  /**
   * Instantiates a new Role query service.
   *
   * @param roleRepository the role repository
   * @param mapper         the mapper
   */
  public RoleQueryServiceImpl(RoleRepository roleRepository,
      RoleMapper mapper) {
    super(roleRepository, mapper);
  }
}
