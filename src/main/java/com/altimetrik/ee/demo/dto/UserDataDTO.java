package com.altimetrik.ee.demo.dto;

import com.altimetrik.ee.demo.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class UserDataDTO {

  @Schema(description = "Username of the user", example = "john_doe")
  private String username;

  @Schema(description = "Email address of the user", example = "john.doe@example.com")
  private String email;

  @Schema(description = "Password of the user", example = "securepassword123")
  private String password;

  @Schema(description = "Roles assigned to the user", example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]")
  private List<Role> roles;
}
