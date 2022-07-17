package com.liem.userservice.service.auth.service;

import com.liem.userservice.dto.user.UserDto;
import com.application.common.auth.token.TokenGenerator;
import com.liem.userservice.service.command.UserCommandService;
import com.liem.userservice.service.query.RefreshTokenQueryService;
import com.liem.userservice.service.query.UserQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Token authenticate service.
 */
@Service
@Slf4j
@Transactional(readOnly = true)
public class TokenAuthenticateService
    extends AbstractAuthenticateService<UserDto<Long>, String> {

  /**
   * The Token generator.
   */
  protected final TokenGenerator<UserDto<Long>, String> tokenGenerator;

  /**
   * Instantiates a new Token authenticate service.
   *
   * @param authenticationManager    the authentication manager
   * @param userQueryService         the user query service
   * @param encoder                  the encoder
   * @param userCommandService       the user command service
   * @param tokenGenerator           the token generator
   */
  public TokenAuthenticateService(
      AuthenticationManager authenticationManager,
      UserQueryService userQueryService,
      PasswordEncoder encoder,
      UserCommandService userCommandService,
      TokenGenerator<UserDto<Long>, String> tokenGenerator) {
    super(authenticationManager, userQueryService, userCommandService, encoder);
    this.tokenGenerator = tokenGenerator;
  }

  /**
   * Authenticate string.
   *
   * @param credential the credential
   * @return the string
   */
  @Override
  public String authenticate(UserDto<Long> credential) {
    final var username = credential.getUsername();
    final var password = credential.getPassword();
    final var authentication = createAuthentication(username, password);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    var userDto = this.userQueryService.getActiveUserByUsername(username);
    return this.tokenGenerator.generateToken(userDto);
  }

  /**
   * Create authentication.
   *
   * @param username the username
   * @param password the password
   * @return the authentication
   */
  private Authentication createAuthentication(
      final String username, final String password) {
    return authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password));
  }
}
