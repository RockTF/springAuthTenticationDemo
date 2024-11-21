package com.altimetrik.ee.demo.controller;

import com.altimetrik.ee.demo.dto.UserResponseDTO;
import com.altimetrik.ee.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserService userService;

  @Autowired private ModelMapper modelMapper;

  @GetMapping(value = "/{username}")
  @Operation(
      summary = "Search user by username",
      description = "Find a user by their username",
      security = @SecurityRequirement(name = "Authorization"),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User found",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Something went wrong"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "The user doesn't exist"),
        @ApiResponse(responseCode = "500", description = "Expired or invalid JWT token")
      })
  public UserResponseDTO search(@PathVariable String username) {
    return modelMapper.map(userService.search(username), UserResponseDTO.class);
  }
}
