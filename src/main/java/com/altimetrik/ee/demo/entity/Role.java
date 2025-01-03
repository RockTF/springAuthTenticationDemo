package com.altimetrik.ee.demo.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_ADMIN,
  ROLE_USER;

  public String getAuthority() {
    return name();
  }
}
