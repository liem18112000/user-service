package com.liem.userservice.dto.role;

import com.application.common.dto.BaseProjection;
import com.liem.userservice.entity.RoleEntity;
import java.util.Set;
import org.springframework.data.rest.core.config.Projection;

/**
 * The interface Role projection.
 */
@Projection(name = "withUsers", types = {RoleEntity.class})
public interface RoleWithUsersProjection extends RoleProjection {

  /**
   * Gets id.
   *
   * @return the id
   */
  Long getId();

  /**
   * Gets users.
   *
   * @return the users
   */
  Set<BaseProjection<Long>> getUsers();

}
