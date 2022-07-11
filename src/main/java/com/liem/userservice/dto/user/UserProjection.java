package com.liem.userservice.dto.user;

import com.application.common.dto.BaseProjection;
import com.liem.userservice.dto.role.RoleProjection;
import com.liem.userservice.entity.UserEntity;
import java.util.Set;
import org.springframework.data.rest.core.config.Projection;

/**
 * The interface User projection.
 */
@Projection(types = {UserEntity.class})
public interface UserProjection extends BaseProjection<Long> {

  /**
   * Gets id.
   *
   * @return the id
   */
  Long getId();

  /**
   * Gets username.
   *
   * @return the username
   */
  String getUsername();


  /**
   * Gets roles.
   *
   * @return the roles
   */
  Set<BaseProjection<Long>> getRoles();

  /**
   * Gets password.
   *
   * @return the password
   */
  String getPassword();

  /**
   * Gets active.
   *
   * @return the active
   */
  boolean getActive();

  /**
   * Gets locked.
   *
   * @return the locked
   */
  boolean getLocked();

  /**
   * Gets expired.
   *
   * @return the expired
   */
  boolean getExpired();
}
