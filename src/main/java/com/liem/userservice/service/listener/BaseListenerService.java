package com.liem.userservice.service.listener;

import com.application.common.dto.BaseDto;
import com.application.common.entity.BaseEntity;
import com.application.common.message.common.MessageOrigin;
import com.application.common.message.listener.MessageListener;
import com.application.common.message.listener.impl.AbstractRestListenerService;
import com.application.common.service.CommandService;
import com.application.common.service.QueryService;
import com.liem.userservice.config.common.AppConfig;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Abstract rest listener service.
 *
 * @param <ID>              the type parameter
 * @param <DTO>             the type parameter
 * @param <ENTITY>          the type parameter
 * @param <QUERY_SERVICE>   the type parameter
 * @param <COMMAND_SERVICE> the type parameter
 */
@Slf4j
abstract public class BaseListenerService<
    ID extends Serializable,
    DTO extends BaseDto<ID>,
    ENTITY extends BaseEntity<ID>,
    QUERY_SERVICE extends QueryService<ID, ENTITY, DTO>,
    COMMAND_SERVICE extends CommandService<ID, DTO>
    > extends AbstractRestListenerService<ID, DTO, ENTITY,
    QUERY_SERVICE, COMMAND_SERVICE>
    implements MessageListener<ID, DTO> {

  /**
   * The App config.
   */
  protected final AppConfig appConfig;

  /**
   * Instantiates a new Abstract rest listener service.
   *
   * @param queryService   the query service
   * @param commandService the command service
   * @param appConfig      the app config
   */
  public BaseListenerService(
      QUERY_SERVICE queryService, COMMAND_SERVICE commandService,
      AppConfig appConfig) {
    super(queryService, commandService);
    this.appConfig = appConfig;
  }

  /**
   * Create response origin message origin.
   *
   * @return the message origin
   */
  protected MessageOrigin createResponseOrigin() {
    var origin = new MessageOrigin();
    origin.setOriginService(appConfig.getApplicationName());
    origin.setOriginServer(appConfig.getApplicationServer());
    origin.setOriginEndpoint(appConfig.getApplicationEndpoint());
    return origin;
  }

}
