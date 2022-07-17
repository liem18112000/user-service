/*
 * Copyright (c) 2022. Liem Doan
 */

package com.liem.userservice.config.common;

import com.application.common.service.CacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Global App config variables
 */
@RequiredArgsConstructor(onConstructor_={@Autowired})
@Configuration
public class AppConfig {

  /**
   * The constant SPRING_APPLICATION_NAME.
   */
  public static final String SPRING_APPLICATION_NAME = "spring.application.name";

  /**
   * The constant SPRING_APPLICATION_SERVER.
   */
  public static final String SPRING_APPLICATION_SERVER = "spring.application.server";

  /**
   * The constant SPRING_APPLICATION_ENDPOINT.
   */
  public static final String SPRING_APPLICATION_ENDPOINT = "spring.application.endpoint";

  /**
   * The Cache service.
   */
  private final CacheService<String, String> cacheService;

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
   * Create object mapper object mapper.
   *
   * @return the object mapper
   */
  @Bean
  public ObjectMapper createObjectMapper() {
    var objectMapper = new ObjectMapper();
    return objectMapper.registerModule(new JavaTimeModule());
  }

  /**
   * Gets application name.
   *
   * @return the application name
   */
  public String getApplicationName() {
    return this.cacheService.get(
        SPRING_APPLICATION_NAME,
        () -> this.applicationName,
        Duration.ofMinutes(60)
    );
  }

  /**
   * Gets application server.
   *
   * @return the application server
   */
  public String getApplicationServer() {
    return this.cacheService.get(
        SPRING_APPLICATION_SERVER,
        () -> this.applicationServer,
        Duration.ofMinutes(60)
    );
  }

  /**
   * Gets application endpoint.
   *
   * @return the application endpoint
   */
  public String getApplicationEndpoint() {
    return this.cacheService.get(
        SPRING_APPLICATION_ENDPOINT,
        () -> this.applicationEndpoint,
        Duration.ofMinutes(60)
    );
  }
}
