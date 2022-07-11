package com.liem.userservice.service.auth.encoder;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The type State password encoder.
 */
@Data
@AllArgsConstructor
public class StatePasswordEncoder implements PasswordEncoder {

  /**
   * The constant PASSWORD_DELIMITER.
   */
  public static final String PASSWORD_DELIMITER = "@";

  /**
   * The State.
   */
  private PasswordEncoderState state;

  /**
   * Instantiates a new State password encoder.
   */
  public StatePasswordEncoder() {
    this.state = PasswordEncoderState.BCRYPT;
  }

  /**
   * Encode string.
   *
   * @param rawPassword the raw password
   * @return the string
   */
  @Override
  public String encode(CharSequence rawPassword) {
    final var stateName = this.state.name();
    final var encodedPassword = this.state.getEncoder().encode(rawPassword);
    return stateName + PASSWORD_DELIMITER + encodedPassword;
  }

  /**
   * Matches boolean.
   *
   * @param rawPassword     the raw password
   * @param encodedPassword the encoded password
   * @return the boolean
   */
  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    final var splitPassword = encodedPassword.split(PASSWORD_DELIMITER);
    if (splitPassword.length < 2) {
      return false;
    }

    final var encodeMode = splitPassword[0];
    final String matchedPassword;
    if (splitPassword.length == 2) {
      matchedPassword = splitPassword[1];
    } else {
      matchedPassword = encodedPassword.replaceFirst(
          splitPassword[0].concat(PASSWORD_DELIMITER), "");
    }

    final var encoderForMode = Arrays.stream(PasswordEncoderState.values())
        .filter(s -> s.name().equals(encodeMode)).findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("Encode mode '%s' is not found", encodeMode)));

    return encoderForMode.getEncoder().matches(rawPassword, matchedPassword);
  }
}
