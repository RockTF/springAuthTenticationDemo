package com.altimetrik.ee.demo.security;

import com.altimetrik.ee.demo.security.exceptionhandrel.RestAuthenticationEntryPoint;
import com.altimetrik.ee.demo.security.filter.JwtTokenFilter;
import com.altimetrik.ee.demo.security.filter.JwtTokenFilterConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;
  @Autowired
  RestAuthenticationEntryPoint restAuthenticationEntryPoint;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(restAuthenticationEntryPoint))

            .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure())
            .authorizeHttpRequests((requests) -> requests
                    .requestMatchers(
                            "/users/signin",
                            "/users/signup",
                            "/users/{username}",
                            "/h2-console/**/**",
                            "/h2-console/**",
                            "/v2/api-docs",
                            "/swagger-resources/**",
                            "/swagger-ui.html",
                            "/configuration/**",
                            "/webjars/**",
                            "/public",
                            "/h2-console/**"
                    ).permitAll().anyRequest().authenticated()
            );

    http.addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)); //clickjacking
    http.formLogin(withDefaults());
    http.httpBasic(withDefaults());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }


//  @Bean
//  public CompromisedPasswordChecker compromisedPasswordChecker() {
//    return new HaveIBeenPwnedRestApiPasswordChecker();
//  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
