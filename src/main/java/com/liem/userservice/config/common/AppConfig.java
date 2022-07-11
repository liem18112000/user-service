/*
 * Copyright (c) 2022. Liem Doan
 */

package com.liem.userservice.config.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Global App config variables
 */
@Getter
@Configuration
public class AppConfig {

  /**
   * The Application name.
   */
  @Value("${spring.application.name}")
  private String applicationName;

  /**
   * The Application server.
   */
  @Value("${spring.application.server}")
  private String applicationServer;

  /**
   * The Application endpoint.
   */
  @Value("${spring.application.endpoint}")
  private String applicationEndpoint;

  /**
   * The constant ID.
   */
  public static final String ID = "id";

  /**
   * The constant PACKAGES.
   */
  public static final String[] PACKAGES = {
      "com.application.common",
      "com.liem"
  };

  @Bean
  public ObjectMapper createObjectMapper() {
    return new ObjectMapper();
  }

}
