package com.liem.userservice.service.client;

import com.application.common.dto.BaseDto;
import com.application.common.message.BaseMessage;
import com.application.common.message.client.MessageCommandClient;
import com.application.common.message.client.MessageQueryClient;
import com.application.common.message.client.impl.AbstractMessageClient;
import com.application.common.message.common.MessageOrigin;
import com.liem.userservice.config.common.AppConfig;
import com.liem.userservice.config.message.RabbitMQConfiguration;
import com.liem.userservice.service.listener.impl.UserMessageType;
import java.io.Serializable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * The type Message client.
 *
 * @param <ID>  the type parameter
 * @param <DTO> the type parameter
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class MessageClient
    <ID extends Serializable, DTO extends BaseDto<ID>>
    extends AbstractMessageClient<ID, DTO>
    implements MessageQueryClient<ID, DTO>, MessageCommandClient<ID, DTO> {

  /**
   * The Rabbit mq configuration.
   */
  private final RabbitMQConfiguration rabbitMQConfiguration;

  /**
   * The App config.
   */
  private final AppConfig appConfig;

  /**
   * The Rabbit template.
   */
  private final RabbitTemplate rabbitTemplate;

  /**
   * Refresh access token base message.
   *
   * @param dto the dto
   * @return the base message
   */
  public BaseMessage<DTO> refreshAccessToken(DTO dto) {
    return getMessageClientTemplate(() -> {
      var request = prepareRequest(dto,
          UserMessageType.REFRESH_ACCESS_TOKEN.name());
      log.info("Refresh access token: {}", request);
      return request;
    }, this::handleFailedRequest);
  }

  /**
   * Authenticate base message.
   *
   * @param dto the dto
   * @return the base message
   */
  public BaseMessage<DTO> authenticate(DTO dto) {
    return getMessageClientTemplate(() -> {
      var request = prepareRequest(dto,
          UserMessageType.AUTHENTICATE.name());
      log.info("Authenticate: {}", request);
      return request;
    }, this::handleFailedAuthenticate);
  }

  /**
   * Handle failed authenticate base message.
   *
   * @param request the request
   * @return the base message
   */
  protected BaseMessage<DTO> handleFailedAuthenticate(BaseMessage<DTO> request) {
    var failedResponse = new BaseMessage<DTO>();
    failedResponse.setBody(request.getBody());
    failedResponse.setOrigin(this.createRequestOrigin());
    request.setMessageType(UserMessageType.AUTHENTICATE.name());
    failedResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
    return failedResponse;
  }

  /**
   * Register base message.
   *
   * @param dto the dto
   * @return the base message
   */
  public BaseMessage<DTO> register(DTO dto) {
    return getMessageClientTemplate(() -> {
      var request = prepareRequest(dto,
          UserMessageType.REGISTER.name());
      log.info("Register: {}", request);
      return request;
    }, this::handleFailedRegister);
  }

  /**
   * Handle failed register base message.
   *
   * @param request the request
   * @return the base message
   */
  protected BaseMessage<DTO> handleFailedRegister(BaseMessage<DTO> request) {
    var failedResponse = new BaseMessage<DTO>();
    failedResponse.setBody(request.getBody());
    failedResponse.setOrigin(this.createRequestOrigin());
    request.setMessageType(UserMessageType.REGISTER.name());
    failedResponse.setHttpStatus(HttpStatus.BAD_REQUEST);
    return failedResponse;
  }

  /**
   * Send message object.
   *
   * @param request the request
   * @return the object
   */
  @Override
  protected Object sendMessage(BaseMessage<DTO> request) {
    return this.rabbitTemplate.convertSendAndReceive(
        this.rabbitMQConfiguration.getExchangeString(),
        this.rabbitMQConfiguration.getRoutingKeyName(),
        request);
  }

  /**
   * Create request origin message origin.
   *
   * @return the message origin
   */
  @Override
  protected MessageOrigin createRequestOrigin() {
    var origin = new MessageOrigin();
    origin.setOriginService(appConfig.getApplicationName());
    origin.setOriginServer(appConfig.getApplicationServer());
    origin.setOriginEndpoint(appConfig.getApplicationEndpoint());
    return origin;
  }
}
