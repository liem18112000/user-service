package com.liem.userservice.service.listener.impl;

import com.application.common.auth.service.TokenRefreshService;
import com.application.common.message.BaseMessage;
import com.liem.userservice.config.common.AppConfig;
import com.liem.userservice.dto.user.UserDto;
import com.liem.userservice.entity.UserEntity;
import com.application.common.auth.service.AuthenticateService;
import com.liem.userservice.service.command.UserCommandService;
import com.liem.userservice.service.listener.BaseListenerService;
import com.liem.userservice.service.query.RefreshTokenQueryService;
import com.liem.userservice.service.query.UserQueryService;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * The type User listener service.
 */
@Slf4j
@Service
public class UserListenerService
    extends BaseListenerService<
            Long, UserDto<Long>, UserEntity,
            UserQueryService, UserCommandService> {

  /**
   * The Authenticate service.
   */
  protected final AuthenticateService<UserDto<Long>, String> authenticateService;

  /**
   * The Refresh token query service.
   */
  protected final RefreshTokenQueryService refreshTokenQueryService;

  /**
   * The Token refresh service.
   */
  protected final TokenRefreshService<UserDto<Long>, String> tokenRefreshService;

  /**
   * Instantiates a new Abstract rest listener service.
   *
   * @param queryService             the query service
   * @param commandService           the command service
   * @param appConfig                the app config
   * @param authenticateService      the authenticate service
   * @param refreshTokenQueryService the refresh token query service
   * @param tokenRefreshService      the token refresh service
   */
  public UserListenerService(UserQueryService queryService,
      UserCommandService commandService, AppConfig appConfig,
      AuthenticateService<UserDto<Long>, String> authenticateService,
      RefreshTokenQueryService refreshTokenQueryService,
      TokenRefreshService<UserDto<Long>, String> tokenRefreshService) {
    super(queryService, commandService, appConfig);
    this.authenticateService = authenticateService;
    this.refreshTokenQueryService = refreshTokenQueryService;
    this.tokenRefreshService = tokenRefreshService;
  }

  /**
   * Init request handle map map.
   *
   * @return the map
   */
  @Override
  protected Map<String, Function<
      BaseMessage<UserDto<Long>>, BaseMessage<UserDto<Long>>>> initRequestHandleMap() {
    final var map =
        super.initRequestHandleMap();
    map.put(UserMessageType.AUTHENTICATE.name(), this::handleAuthenticate);
    map.put(UserMessageType.REGISTER.name(), this::handleRegister);
    map.put(UserMessageType.REFRESH_ACCESS_TOKEN.name(), this::handleRefreshAccessToken);
    return map;
  }

  /**
   * Handle register base message.
   *
   * @param userDtoBaseMessage the user dto base message
   * @return the base message
   */
  protected BaseMessage<UserDto<Long>> handleRegister(
      BaseMessage<UserDto<Long>> userDtoBaseMessage) {
    var body = userDtoBaseMessage.getBody();
    try {
      final var user = this.commandService.create(body);
      userDtoBaseMessage.setBody(user);
      userDtoBaseMessage.setHttpStatus(HttpStatus.CREATED);
      userDtoBaseMessage.setOrigin(this.createResponseOrigin());
      return userDtoBaseMessage;
    } catch (Exception exception) {
      exception.printStackTrace();
      return this.handleFailedRegister(userDtoBaseMessage, exception.getMessage());
    }
  }

  /**
   * Handle authenticate base message.
   *
   * @param userDtoBaseMessage the user dto base message
   * @return the base message
   */
  protected BaseMessage<UserDto<Long>> handleAuthenticate(
      BaseMessage<UserDto<Long>> userDtoBaseMessage) {
    var body = userDtoBaseMessage.getBody();
    final var username = body.getUsername();
    try {
      final var accessToken = this.authenticateService.authenticate(body);
      final var refreshToken = this.refreshTokenQueryService
          .getActiveTokenByUsername(username);
      userDtoBaseMessage.getBody().setToken(accessToken);
      userDtoBaseMessage.getBody().setRefreshToken(refreshToken);
      userDtoBaseMessage.setHttpStatus(HttpStatus.OK);
      userDtoBaseMessage.setOrigin(this.createResponseOrigin());
      return userDtoBaseMessage;
    } catch (Exception exception) {
      exception.printStackTrace();
      return this.handleFailedAuthenticate(
          userDtoBaseMessage, exception.getMessage());
    }
  }

  /**
   * Handle refresh access token base message.
   *
   * @param userDtoBaseMessage the user dto base message
   * @return the base message
   */
  protected BaseMessage<UserDto<Long>> handleRefreshAccessToken(
      BaseMessage<UserDto<Long>> userDtoBaseMessage) {
    var body = userDtoBaseMessage.getBody();
    final var refreshToken = body.getRefreshToken();
    try {
      final var accessToken = this.tokenRefreshService.refreshToken(body);
      userDtoBaseMessage.getBody().setToken(accessToken);
      userDtoBaseMessage.getBody().setRefreshToken(refreshToken);
      userDtoBaseMessage.setHttpStatus(HttpStatus.OK);
      userDtoBaseMessage.setOrigin(this.createResponseOrigin());
      return userDtoBaseMessage;
    } catch (Exception exception) {
      exception.printStackTrace();
      return this.handleFailedRefreshAccessToken(
          userDtoBaseMessage, exception.getMessage());
    }
  }

  private BaseMessage<UserDto<Long>> handleFailedRefreshAccessToken(
      BaseMessage<UserDto<Long>> response, String message) {
    log.error(message);
    response.setBody(null);
    response.setErrorMessage(message);
    response.setOrigin(createResponseOrigin());
    response.setHttpStatus(HttpStatus.BAD_REQUEST);
    return response;
  }

  /**
   * Handle failed authenticate base message.
   *
   * @param response the response
   * @param message  the message
   * @return the base message
   */
  protected BaseMessage<UserDto<Long>> handleFailedAuthenticate(
      BaseMessage<UserDto<Long>> response, String message) {
    log.error(message);
    response.setBody(null);
    response.setErrorMessage(message);
    response.setOrigin(createResponseOrigin());
    response.setHttpStatus(HttpStatus.UNAUTHORIZED);
    return response;
  }

  /**
   * Handle failed register base message.
   *
   * @param response the response
   * @param message  the message
   * @return the base message
   */
  protected BaseMessage<UserDto<Long>> handleFailedRegister(
      BaseMessage<UserDto<Long>> response, String message) {
    log.error(message);
    response.setBody(null);
    response.setErrorMessage(message);
    response.setOrigin(createResponseOrigin());
    response.setHttpStatus(HttpStatus.BAD_REQUEST);
    return response;
  }

  /**
   * Handle request rest message.
   *
   * @param message the rest message
   * @return the rest message
   */
  @Override
  @RabbitListener(queues = "${spring.rabbitmq.queue}")
  public BaseMessage<UserDto<Long>> handleRequest(
      final BaseMessage<UserDto<Long>> message) {
    return super.handleRequest(message);
  }

}
