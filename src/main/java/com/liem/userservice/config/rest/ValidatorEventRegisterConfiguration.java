package com.liem.userservice.config.rest;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

/**
 * The type Validator event register configuration.
 */
@Configuration
public class ValidatorEventRegisterConfiguration
    implements InitializingBean {

  /**
   * The Validating repository event listener.
   */
  @Autowired
  private ValidatingRepositoryEventListener validatingRepositoryEventListener;

  /**
   * The Validators.
   */
  @Autowired
  private Map<String, Validator> validators;

  /**
   * After properties set.
   */
  @Override
  public void afterPropertiesSet() {
    List<String> events = List.of(
        "beforeCreate",
        "beforeSave",
        "beforeDelete"
    );
    for (Map.Entry<String, Validator> entry : validators.entrySet()) {
      events.stream()
          .filter(p -> entry.getKey().startsWith(p))
          .findFirst()
          .ifPresent(
              p -> validatingRepositoryEventListener
                  .addValidator(p, entry.getValue()));
    }
  }
}
