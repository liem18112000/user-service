package com.liem.userservice.service.auth.encoder;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The enum Password encoder state.
 */
@Getter
public enum PasswordEncoderState {

  /**
   * The Noop.
   */
  NOOP(new PasswordEncoder() {
    @Override
    public String encode(CharSequence rawPassword) {
      return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
      return rawPassword.toString().equals(encodedPassword);
    }
  }),

  /**
   * The Bcrypt.
   */
  BCRYPT(new BCryptPasswordEncoder());

  /**
   * The Encoder.
   */
  private final PasswordEncoder encoder;

  /**
   * Instantiates a new Password encoder state.
   *
   * @param encoder the encoder
   */
  PasswordEncoderState(
      final @NotNull PasswordEncoder encoder) {
    this.encoder = encoder;
  }
}
