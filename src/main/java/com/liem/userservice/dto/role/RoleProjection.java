package com.liem.userservice.dto.role;

import com.application.common.dto.BaseProjection;
import com.liem.userservice.entity.RoleEntity;
import java.util.Set;
import org.springframework.data.rest.core.config.Projection;

/**
 * The interface Role projection.
 */
@Projection(types = {RoleEntity.class})
public interface RoleProjection extends BaseProjection<Long> {

  /**
   * Gets id.
   *
   * @return the id
   */
  Long getId();

  /**
   * Gets active.
   *
   * @return the active
   */
  boolean getActive();

}
