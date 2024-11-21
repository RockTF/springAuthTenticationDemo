package com.altimetrik.ee.demo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

  private static final Logger logger = LoggerFactory.getLogger(KeycloakRoleConverter.class);

  @Override
  public Collection<GrantedAuthority> convert(Jwt source) {
    Object realmAccessObj = source.getClaims().get("realm_access");
    if (!(realmAccessObj instanceof Map<?, ?> realmAccess)) {
      logger.warn("No realm_access claim found in JWT");
      return new ArrayList<>();
    }

    Object rolesObj = realmAccess.get("roles");
    if (!(rolesObj instanceof List<?> roles)) {
      logger.warn("No roles found in realm_access claim");
      return new ArrayList<>();
    }

    Collection<GrantedAuthority> authorities =
        roles.stream()
            .filter(role -> role instanceof String)
            .map(role -> "ROLE_" + role)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

    logger.info("Converted roles: {}", authorities);
    return authorities;
  }
}
