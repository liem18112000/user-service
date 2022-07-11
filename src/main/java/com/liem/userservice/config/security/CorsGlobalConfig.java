/*
 * Copyright (c) 2021-2022. Liem Doan
 */

package com.liem.userservice.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS Global configuration
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/HTTP/CORS">Cross-Origin Resource
 * Sharing</a>
 */
@Configuration
@Profile("cors")
public class CorsGlobalConfig implements WebMvcConfigurer {

  /**
   * Add cors mappings.
   *
   * @param registry the registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("OPTIONS", "GET", "POST", "PUT", "DELETE")
        .allowedHeaders("Access-Control-Allow-Origin", "Origin", "X-Requested-With", "Content-Type",
            "Accept",
            "X-XSRF-TOKEN", "X-CSRF-TOKEN", "Authentication", "Authorization");
    WebMvcConfigurer.super.addCorsMappings(registry);
  }
}
