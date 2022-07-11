package com.liem.userservice.config.message;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.ConfirmType;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Rabbit mq configuration.
 */
@Slf4j
@Configuration
@Getter
public class RabbitMQConfiguration {

  /**
   * The Host.
   */
  @Value("${spring.rabbitmq.host}")
  private String host;

  /**
   * The Username.
   */
  @Value("${spring.rabbitmq.username}")
  private String username;

  /**
   * The Password.
   */
  @Value("${spring.rabbitmq.password}")
  private String password;

  /**
   * The Queue.
   */
  @Value("${spring.rabbitmq.queue}")
  private String queueName;

  /**
   * The Exchange.
   */
  @Value("${spring.rabbitmq.exchange}")
  private String exchangeName;

  /**
   * The Routing key.
   */
  @Value("${spring.rabbitmq.routing-key}")
  private String routingKeyName;

  /**
   * The Durable.
   */
  @Value("${spring.rabbitmq.durable}")
  private boolean durable;

  /**
   * The Virtual host.
   */
  @Value("${spring.rabbitmq.virtual-host}")
  private String virtualHost;

  /**
   * The Publisher returns.
   */
  @Value("${spring.rabbitmq.publisher-returns}")
  private boolean publisherReturns;

  /**
   * The Publisher confirm type.
   */
  @Value("${spring.rabbitmq.publisher-confirm-type}")
  private ConfirmType publisherConfirmType;

  /**
   * The Cache mode.
   */
  @Value("${spring.rabbitmq.cache.connection.mode}")
  private CacheMode cacheMode;

  /**
   * Connection factory caching connection factory.
   *
   * @return the caching connection factory
   */
  @Bean
  CachingConnectionFactory connectionFactory() {
    CachingConnectionFactory cachingConnectionFactory =
        new CachingConnectionFactory();
    cachingConnectionFactory.setHost(host);
    cachingConnectionFactory.setUsername(username);
    cachingConnectionFactory.setPassword(password);
    cachingConnectionFactory.setVirtualHost(virtualHost);
    cachingConnectionFactory.setCacheMode(cacheMode);
    cachingConnectionFactory.setPublisherReturns(publisherReturns);
    cachingConnectionFactory.setPublisherConfirmType(publisherConfirmType);
    return cachingConnectionFactory;
  }

  /**
   * Json message converter message converter.
   *
   * @return the message converter
   */
  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  /**
   * Rabbit template rabbit template.
   *
   * @param connectionFactory the connection factory
   * @return the rabbit template
   */
  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(jsonMessageConverter());
    rabbitTemplate.setMandatory(true);
    return rabbitTemplate;
  }

  /**
   * Gets exchange.
   *
   * @return the exchange
   */
  @Bean
  Exchange getExchangeName() {
    return ExchangeBuilder
        .directExchange(exchangeName)
        .durable(durable)
        .build();
  }

  /**
   * Gets exchange string.
   *
   * @return the exchange string
   */
  public String getExchangeString() {
    return this.exchangeName;
  }

  /**
   * Queue queue.
   *
   * @return the queue
   */
  @Bean
  Queue queue() {
    return new Queue(queueName, durable);
  }

  /**
   * Binding binding.
   *
   * @param queue    the queue
   * @param exchange the exchange
   * @return the binding
   */
  @Bean
  Binding binding(Queue queue, Exchange exchange) {
    return BindingBuilder
        .bind(queue)
        .to(exchange)
        .with(routingKeyName)
        .noargs();
  }

  /**
   * Rabbit admin rabbit admin.
   *
   * @param connectionFactory the connection factory
   * @return the rabbit admin
   */
  @Bean
  public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
    return new RabbitAdmin(connectionFactory);
  }

  /**
   * Rabbit listener endpoint registry rabbit listener endpoint registry.
   *
   * @return the rabbit listener endpoint registry
   */
  @Bean
  public RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry() {
    return new RabbitListenerEndpointRegistry();
  }
}
