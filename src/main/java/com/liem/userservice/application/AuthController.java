package com.liem.userservice.application;

import com.application.common.message.BaseMessage;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liem.userservice.dto.user.UserDto;
import com.liem.userservice.service.client.MessageClient;
import java.util.LinkedHashMap;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Auth controller.
 */
@RestController
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class AuthController {

  /**
   * The Message client.
   */
  private final MessageClient<Long, UserDto<Long>> messageClient;

  /**
   * The Object mapper.
   */
  private final ObjectMapper objectMapper;

  /**
   * Ping auth guest response entity.
   *
   * @return the response entity
   */
  @RequestMapping("auth/ping/no-auth")
  public ResponseEntity<?> pingNoAuth() {
    return ResponseEntity.ok("Pong");
  }

  /**
   * Ping auth guest response entity.
   *
   * @return the response entity
   */
  @RequestMapping("auth/ping/guest")
  public ResponseEntity<?> pingAuthGuest() {
    return ResponseEntity.ok("Guest Pong");
  }

  /**
   * Ping auth viewer response entity.
   *
   * @return the response entity
   */
  @RequestMapping("auth/ping/viewer")
  public ResponseEntity<?> pingAuthViewer() {
    return ResponseEntity.ok("Viewer Pong");
  }

  /**
   * Ping auth admin response entity.
   *
   * @return the response entity
   */
  @RequestMapping("auth/ping/admin")
  public ResponseEntity<?> pingAuthAdmin() {
    return ResponseEntity.ok("Admin Pong");
  }

  /**
   * Ping auth super admin response entity.
   *
   * @return the response entity
   */
  @RequestMapping("auth/ping/super-admin")
  public ResponseEntity<?> pingAuthSuperAdmin() {
    return ResponseEntity.ok("Super admin Pong");
  }

  /**
   * Authenticate response entity.
   *
   * @param userDto the user dto
   * @return the response entity
   */
  @PostMapping("login")
  public ResponseEntity<?> authenticate(@RequestBody UserDto<Long> userDto) {
    final var response =
        this.objectMapper.convertValue(this.messageClient.authenticate(userDto),
            new TypeReference<BaseMessage<LinkedHashMap<String, Object>>>() {});
    final var status = response.getHttpStatus();
    final var body = response.getBody();
    final var errorMessage = response.getErrorMessage();
    return ResponseEntity.status(Optional.ofNullable(status).orElse(HttpStatus.UNAUTHORIZED))
        .body(Optional.ofNullable(body).map(b -> b.get("token")).orElse(errorMessage));
  }

  /**
   * Register user response entity.
   *
   * @param userDto the user dto
   * @return the response entity
   */
  @PostMapping("register")
  public ResponseEntity<?> registerUser(@RequestBody @Valid UserDto<Long> userDto) {
    final var response = this.messageClient.register(userDto);
    final var status = response.getHttpStatus();
    final var errorMessage = response.getErrorMessage();
    return ResponseEntity.status(status)
        .body(status == HttpStatus.CREATED ? response : errorMessage);
  }
}
