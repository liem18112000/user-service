package com.liem.userservice.dto.auth;

import com.application.common.auth.token.enums.TokenType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

/**
 * The type Access token response dto.
 */
@Builder(toBuilder = true)
@Data
@JsonInclude(Include.NON_NULL)
public class AccessTokenResponseDto {

  /**
   * The Error message.
   */
  protected String errorMessage;

  /**
   * The Access token.
   */
  protected String accessToken;

  /**
   * The Access token type.
   */
  @Builder.Default
  protected String accessTokenType = TokenType.Bearer.name();

  /**
   * The Refresh token.
   */
  protected String refreshToken;

  /**
   * The Principal.
   */
  protected String principal;
}
