package com.liem.userservice.service.auth.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

/**
 * The type Authenticate entry point.
 */
@Slf4j
@Service
public class AuthenticateEntryPointImpl implements AuthenticationEntryPoint {

  /**
   * Commence.
   *
   * @param request       the request
   * @param response      the response
   * @param authException the auth exception
   * @throws IOException      the io exception
   * @throws ServletException the servlet exception
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    final var message = authException.getMessage();
    log.error("Unauthorized error: {}", message);
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getOutputStream().println("{ \"error\": \"" + message + "\" }");
  }

}
