package com.altimetrik.ee.demo.dto;

import com.altimetrik.ee.demo.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class UserResponseDTO {

  @Schema(description = "Unique identifier of the user", example = "1")
  private Integer id;

  @Schema(description = "Username of the user", example = "john_doe")
  private String username;

  @Schema(description = "Email address of the user", example = "john.doe@example.com")
  private String email;

  @Schema(description = "Roles assigned to the user", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
  private List<Role> roles;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

}
