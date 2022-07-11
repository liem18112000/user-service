package com.liem.userservice.service.auth.filter;

import static com.liem.userservice.config.security.JwtConfiguration.AUTHORIZATION_HEADER;

import com.application.common.auth.token.TokenExtractor;
import com.application.common.auth.token.TokenParser;
import com.application.common.auth.token.TokenValidator;
import com.application.common.service.CacheService;
import com.liem.userservice.config.security.JwtConfiguration;
import com.liem.userservice.service.query.UserQueryService;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

/**
 * The type Token authenticate filter.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class TokenAuthenticateFilter extends OncePerRequestFilter {

  /**
   * The constant USER_DETAILS.
   */
  public static final String USER_DETAILS = "userDetails";

  /**
   * The User query service.
   */
  protected final UserQueryService userQueryService;

  /**
   * The Token parser.
   */
  protected final TokenParser<String, String> tokenParser;

  /**
   * The Token validator.
   */
  protected final TokenValidator<String> tokenValidator;

  /**
   * The Token extractor.
   */
  protected final TokenExtractor<String> tokenExtractor;

  /**
   * The Jwt configuration.
   */
  protected final JwtConfiguration jwtConfiguration;

  /**
   * The Cache service.
   */
  protected final CacheService<String, String> cacheService;

  /**
   * The Path matcher.
   */
  protected final AntPathMatcher pathMatcher = new AntPathMatcher();

  /**
   * Do filter internal.
   *
   * @param request     the request
   * @param response    the response
   * @param filterChain the filter chain
   * @throws ServletException the servlet exception
   * @throws IOException      the io exception
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final var requestUri = request.getRequestURI();
    if (!isIgnoredPath(requestUri)) {
      log.info("Path {} start to be authenticated", requestUri);
      try {
        final var parsedToken = this.parseToken(request);
        log.info("Parsed token: {}", parsedToken);
        final var cachedUsername = this.cacheService.get(parsedToken);
        if (cachedUsername != null) {
          handleAuthenticationFromCache(request, cachedUsername);
        } else {
          handleAuthenticationFromDatabase(request, parsedToken);
        }
      } catch (Exception e) {
        e.printStackTrace();
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
      }
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Handle authentication from database.
   *
   * @param request     the request
   * @param parsedToken the parsed token
   */
  protected void handleAuthenticationFromDatabase(
      final @NotNull HttpServletRequest request,
      final @NotNull String parsedToken) {
    final UserDetails userDetails;
    log.info("Token not found in cache");
    if (this.tokenValidator.validate(parsedToken)) {
      log.info("Parsed token is validated");
      final var username = tokenExtractor.getUserFromToken(parsedToken);
      log.info("Username extracted form token: {}", username);
      userDetails = userQueryService.loadUserByUsername(username);
      log.info("User found: {}", userDetails);
      setUpSecurityContext(request, userDetails);
      log.info("Cache JWT token by username: {}", username);
      this.cacheService.cache(parsedToken, username,
          jwtConfiguration.getTokenCacheDuration());
      request.setAttribute(USER_DETAILS, userDetails);
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
          "Token validate failed");
    }
  }

  /**
   * Handle authentication from cache.
   *
   * @param request        the request
   * @param cachedUsername the cached username
   */
  protected void handleAuthenticationFromCache(
      final @NotNull HttpServletRequest request,
      final @NotNull String cachedUsername) {
    final UserDetails userDetails;
    log.info("Token is found in cache with username: {}", cachedUsername);
    userDetails = userQueryService.loadUserByUsername(cachedUsername);
    log.info("User found: {}", userDetails);
    setUpSecurityContext(request, userDetails);
    request.setAttribute(USER_DETAILS, userDetails);
  }

  /**
   * Sets up security context.
   *
   * @param request     the request
   * @param userDetails the user details
   */
  private void setUpSecurityContext(
      final @NotNull HttpServletRequest request,
      final @NotNull UserDetails userDetails) {
    var authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  /**
   * Is ignored path boolean.
   *
   * @param requestUri the request uri
   * @return the boolean
   */
  private boolean isIgnoredPath(final @NotNull String requestUri) {
    final var ignoredPaths = this.jwtConfiguration.getJwtWhiteList();
    if (ignoredPaths == null) {
      return false;
    }
    return Arrays.stream(ignoredPaths.split(","))
        .anyMatch(regex -> this.pathMatcher.match(regex, requestUri));
  }

  /**
   * Parse token string.
   *
   * @param request the request
   * @return the string
   */
  private String parseToken(final @NotNull HttpServletRequest request) {
    final var rawToken = request.getHeader(AUTHORIZATION_HEADER);
    return this.tokenParser.parse(rawToken);
  }
}
