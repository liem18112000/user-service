package com.liem.userservice.service.auth.service;

import com.application.common.auth.service.AuthenticateService;
import com.liem.userservice.service.command.UserCommandService;
import com.liem.userservice.service.query.UserQueryService;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The type Abstract authenticate service.
 *
 * @param <ID>    the type parameter
 * @param <TOKEN> the type parameter
 */
@RequiredArgsConstructor(onConstructor_={@Autowired})
public abstract class AbstractAuthenticateService
    <TOKEN extends Serializable> implements AuthenticateService<TOKEN> {

  /**
   * The Authentication manager.
   */
  protected final AuthenticationManager authenticationManager;

  /**
   * The User query service.
   */
  protected final UserQueryService userQueryService;

  /**
   * The User command service.
   */
  protected final UserCommandService userCommandService;

  /**
   * The Encoder.
   */
  protected final PasswordEncoder encoder;
}
