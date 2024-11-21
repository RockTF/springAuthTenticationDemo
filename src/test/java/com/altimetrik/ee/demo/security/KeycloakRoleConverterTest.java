package com.altimetrik.ee.demo.security;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

class KeycloakRoleConverterTest {
  @Test
  void shouldConvertRolesToGrantedAuthorities() {
    Jwt jwt =
        Jwt.withTokenValue("mock-token")
            .header("alg", "none")
            .claim("realm_access", Map.of("roles", List.of("ADMIN", "USER")))
            .build();

    KeycloakRoleConverter converter = new KeycloakRoleConverter();
    Collection<GrantedAuthority> authorities = converter.convert(jwt);

    assertNotNull(authorities);
    assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")));
    assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
  }

  @Test
  void shouldReturnEmptyListIfNoRolesPresent() {
    Jwt jwt =
        Jwt.withTokenValue("mock-token")
            .header("alg", "none")
            .claim("realm_access", Map.of("roles", List.of()))
            .build();

    KeycloakRoleConverter converter = new KeycloakRoleConverter();
    Collection<GrantedAuthority> authorities = converter.convert(jwt);

    assertNotNull(authorities);
    assertTrue(authorities.isEmpty());
  }
}
