package com.liem.userservice.service.auth.filter;

import static com.liem.userservice.service.auth.filter.TokenAuthenticateFilter.USER_DETAILS;

import com.application.common.service.CacheService;
import com.liem.userservice.config.security.JwtConfiguration;
import com.liem.userservice.dto.resource.ResourceDto;
import com.liem.userservice.dto.role.RoleDto;
import com.liem.userservice.entity.enums.ResourceRoleType;
import com.liem.userservice.service.query.ResourceQueryService;
import com.liem.userservice.service.query.UserQueryService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The type Token authorization filter.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_={@Autowired})
public class TokenAuthorizationFilter extends OncePerRequestFilter {

  /**
   * The Resource query service.
   */
  protected final ResourceQueryService resourceQueryService;

  /**
   * The User query service.
   */
  protected final UserQueryService userQueryService;

  /**
   * The Environment.
   */
  private final JwtConfiguration jwtConfiguration;

  /**
   * The Cache service.
   */
  private final CacheService<String, String> cacheService;

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
      HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    final var requestUri = request.getRequestURI();
    if (!this.isIgnoredPath(requestUri)) {
      final var userDetails = (UserDetails) request.getAttribute(USER_DETAILS);
      log.info("Path {} start to be authorized", requestUri);
      if (userDetails != null) {
        final var username = userDetails.getUsername();
        final var authorizationKey = String.format("%s_%s", username, requestUri);
        final var cachedAuthorization = this.cacheService.get(authorizationKey);
        if (cachedAuthorization != null) {
          log.info("Authorization is found in cache with username_path: {}", authorizationKey);
        } else {
          log.info("Authorization not found in cache");
          handleAuthorizationFromDatabase(response, requestUri, userDetails, authorizationKey);
        }
      }
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Handle authorization from database.
   *
   * @param response         the response
   * @param requestUri       the request uri
   * @param userDetails      the user details
   * @param authorizationKey the authorization key
   * @throws IOException the io exception
   */
  protected void handleAuthorizationFromDatabase(
      @NotNull HttpServletResponse response,
      final @NotNull String requestUri,
      final @NotNull UserDetails userDetails,
      final @NotNull String authorizationKey) throws IOException {
    final var authorities = userDetails.getAuthorities();
    log.info("Granted authorities: {}", Arrays.toString(authorities.toArray()));
    final var matchResource = findMatchRoute(requestUri);
    if (matchResource != null) {
      handleAuthorizationWhenResourceIsFound(
          response, authorizationKey, authorities, matchResource);
    } else {
      log.info("Path '{}' by pass authorized as it is not found in resource",
          requestUri);
    }
  }

  /**
   * Handle authorization when resource is found.
   *
   * @param response         the response
   * @param authorizationKey the authorization key
   * @param authorities      the authorities
   * @param matchResource    the match resource
   * @throws IOException the io exception
   */
  protected void handleAuthorizationWhenResourceIsFound(
      @NotNull HttpServletResponse response,
      final @NotNull String authorizationKey,
      final @NotNull Collection<? extends GrantedAuthority> authorities,
      final @NotNull ResourceDto<Long> matchResource)
      throws IOException {
    log.info("Match resource is found: {}", matchResource);
    final var roleType = matchResource.getResourceRoleType();
    final var roles = matchResource.getRoles();
    if (!this.validateAuthorization(roles, roleType, authorities)) {
      log.error("User not is authorized to access resource");
      response.setContentType("application/json");
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.getOutputStream().println(
          "{\"error\":\"Not authorized to access resource\"}");
    } else {
      log.info("User is authorized to access resource");
      this.cacheService.cache(authorizationKey, matchResource.toString(),
          this.jwtConfiguration.getTokenCacheDuration());
      log.info("Cache authorization by key: {}", authorizationKey);
    }
  }

  /**
   * Validate authorization boolean.
   *
   * @param roles       the roles
   * @param roleType    the role type
   * @param authorities the authorities
   * @return the boolean
   */
  protected boolean validateAuthorization(
      final @NotNull Set<RoleDto<Long>> roles,
      final @NotNull ResourceRoleType roleType,
      final @NotNull Collection<? extends GrantedAuthority> authorities) {
    final var roleNames = roles.stream()
        .map(RoleDto::getName)
        .collect(Collectors.toSet());
    final var authorityNames = authorities.stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toSet());
    switch (roleType) {
      case ALL: return roleNames.containsAll(authorityNames);
      case ANY: return roleNames.stream().anyMatch(authorityNames::contains);
      case NOT: return roleNames.stream().noneMatch(authorityNames::contains);
      default: {
        log.error("Resource role type not handled: {}", roleType);
        return false;
      }
    }
  }

  /**
   * Is ignored path boolean.
   *
   * @param requestUri the request uri
   * @return the boolean
   */
  protected boolean isIgnoredPath(String requestUri) {
    final var ignoredPaths = this.jwtConfiguration.getJwtWhiteList();
    if (ignoredPaths == null) {
      return false;
    }
    return Arrays.stream(ignoredPaths.split(","))
        .anyMatch(regex -> new AntPathMatcher().match(regex, requestUri));
  }

  /**
   * Find match route resource dto.
   *
   * @param requestUri the request uri
   * @return the resource dto
   */
  protected ResourceDto<Long> findMatchRoute(String requestUri) {
    return this.resourceQueryService
        .getAllActiveResources().stream().filter(r ->
            pathMatcher.match(r.getPathPattern(), requestUri))
        .findFirst().orElse(null);
  }
}
