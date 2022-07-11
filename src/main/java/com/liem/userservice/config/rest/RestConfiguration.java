package com.liem.userservice.config.rest;

import com.application.common.dto.BasicInformationProjection;
import com.application.common.dto.KeyValueProjection;
import com.liem.userservice.dto.resource.ResourceProjection;
import com.liem.userservice.dto.role.RoleProjection;
import com.liem.userservice.dto.role.RoleWithUsersProjection;
import com.liem.userservice.dto.user.UserProjection;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

/**
 * The type Rest configuration.
 */
@Configuration
public class RestConfiguration implements RepositoryRestConfigurer {

  /**
   * Configure repository rest configuration.
   *
   * @param config the config
   * @param cors   the cors
   */
  @Override
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config,
      CorsRegistry cors) {
    config
        .getProjectionConfiguration()

        .addProjection(KeyValueProjection.class)
        .addProjection(BasicInformationProjection.class)

        .addProjection(UserProjection.class)

        .addProjection(RoleProjection.class)
        .addProjection(RoleWithUsersProjection.class)

        .addProjection(ResourceProjection.class)
    ;

    RepositoryRestConfigurer.super
        .configureRepositoryRestConfiguration(config, cors);
  }
}
