package com.liem.userservice.service.listener.impl;

/**
 * The enum User message type.
 */
public enum UserMessageType {

  /**
   * Authenticate user message type.
   */
  AUTHENTICATE,

  /**
   * Register user message type.
   */
  REGISTER,

  /**
   * Refresh access token user message type.
   */
  REFRESH_ACCESS_TOKEN
}
