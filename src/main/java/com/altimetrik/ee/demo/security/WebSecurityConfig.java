package com.altimetrik.ee.demo.security;

import com.altimetrik.ee.demo.security.exceptionhandrel.CustomAccessDeniedHandler;
import com.altimetrik.ee.demo.security.exceptionhandrel.RestAuthenticationEntryPoint;
import com.altimetrik.ee.demo.security.filter.CsrfCookieFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class WebSecurityConfig {

  @Autowired RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
    CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler =
        new CsrfTokenRequestAttributeHandler();

    http.csrf(
        csrfConfig ->
            csrfConfig
                .csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                .ignoringRequestMatchers("/contact", "/register")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()));
    http.addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

    http.sessionManagement(
        sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.exceptionHandling(
        exceptions -> exceptions.authenticationEntryPoint(restAuthenticationEntryPoint));

    http.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
        .authorizeHttpRequests(
            (requests) ->
                requests
                    .requestMatchers("/users/{username}")
                    .hasRole("ADMIN")
                    .requestMatchers(
                        "/users/signin",
                        "/users/signup",
                        "/h2-console/**/**",
                        "/h2-console/**",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/configuration/**",
                        "/webjars/**",
                        "/public",
                        "/h2-console/**")
                    .permitAll());

    http.oauth2ResourceServer(
        rsc ->
            rsc.jwt(
                jwtConfigurer ->
                    jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
    http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

    return http.build();
  }
}
