package com.altimetrik.ee.demo.dto;

import com.altimetrik.ee.demo.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class UserResponseDTO {

  @Schema(description = "Unique identifier of the user", example = "1")
  private Integer id;

  @Schema(description = "Username of the user", example = "john_doe")
  private String username;

  @Schema(description = "Email address of the user", example = "john.doe@example.com")
  private String email;

  @Schema(description = "Roles assigned to the user", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
  private List<Role> roles;

  public List<Role> getRoles() {
    return roles == null ? null : new ArrayList<>(roles);
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles == null ? null : new ArrayList<>(roles);
  }
}
