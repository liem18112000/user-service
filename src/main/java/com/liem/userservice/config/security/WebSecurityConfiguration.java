package com.liem.userservice.config.security;

import com.liem.userservice.service.auth.encoder.PasswordEncoderState;
import com.liem.userservice.service.auth.encoder.StatePasswordEncoder;
import com.liem.userservice.service.auth.filter.TokenAuthenticateFilter;
import com.liem.userservice.service.auth.filter.TokenAuthorizationFilter;
import com.liem.userservice.service.query.UserQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Web security configuration.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_={@Autowired})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * The User query service.
   */
  private final UserQueryService userQueryService;

  /**
   * The Authentication entry point.
   */
  private final AuthenticationEntryPoint authenticationEntryPoint;

  /**
   * The Authenticate filter.
   */
  private final TokenAuthenticateFilter authenticateFilter;

  /**
   * The Authorization filter.
   */
  private final TokenAuthorizationFilter authorizationFilter;

  /**
   * The Jwt configuration.
   */
  private final JwtConfiguration jwtConfiguration;

  /**
   * Configure.
   *
   * @param authenticationManagerBuilder the authentication manager builder
   * @throws Exception the exception
   */
  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(this.userQueryService)
        .passwordEncoder(this.createPasswordEncoder());
  }

  /**
   * Create password encoder password encoder.
   *
   * @return the password encoder
   */
  @Bean
  public PasswordEncoder createPasswordEncoder() {
    return new StatePasswordEncoder(PasswordEncoderState.BCRYPT);
  }

  /**
   * Authentication manager bean authentication manager.
   *
   * @return the authentication manager
   * @throws Exception the exception
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean()
      throws Exception {
    return super.authenticationManagerBean();
  }

  /**
   * Configure.
   *
   * @param http the http
   * @throws Exception the exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().and().csrf().disable()
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests().antMatchers(
            jwtConfiguration.getJwtWhiteList().split(",")).permitAll()
        .anyRequest().authenticated();
    http.addFilterBefore(this.authenticateFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterAfter(this.authorizationFilter, this.authenticateFilter.getClass());
  }

}
