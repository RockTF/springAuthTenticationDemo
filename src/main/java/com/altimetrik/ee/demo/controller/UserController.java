package com.altimetrik.ee.demo.controller;

import com.altimetrik.ee.demo.dto.LoginRequestDTO;
import com.altimetrik.ee.demo.dto.UserDataDTO;
import com.altimetrik.ee.demo.dto.UserResponseDTO;
import com.altimetrik.ee.demo.entity.User;
import com.altimetrik.ee.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired private UserService userService;

  @Autowired private ModelMapper modelMapper;

  @PostMapping("/signin")
  @Operation(
      summary = "Sign in",
      description = "Authenticate user with username and password",
      responses = {
        @ApiResponse(responseCode = "200", description = "Successful authentication"),
        @ApiResponse(responseCode = "400", description = "Something went wrong"),
        @ApiResponse(responseCode = "422", description = "Invalid username/password supplied")
      })
  public String signIn(@RequestBody LoginRequestDTO loginRequest) {
    return userService.signIn(loginRequest.username(), loginRequest.password());
  }

  @PostMapping("/signup")
  @Operation(
      summary = "Sign up",
      description = "Register a new user",
      responses = {
        @ApiResponse(responseCode = "200", description = "User successfully registered"),
        @ApiResponse(responseCode = "400", description = "Something went wrong"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "422", description = "Username is already in use")
      })
  public String signUp(@RequestBody UserDataDTO user) {
    return userService.signUp(modelMapper.map(user, User.class));
  }

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

  @GetMapping(value = "/me")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @Operation(
      summary = "Get current user info",
      description = "Retrieve information about the currently authenticated user",
      security = @SecurityRequirement(name = "Authorization"),
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User info retrieved",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Something went wrong"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "500", description = "Expired or invalid JWT token")
      })
  public UserResponseDTO me(HttpServletRequest req) {
    return modelMapper.map(userService.findCurrentUser(req), UserResponseDTO.class);
  }

  @GetMapping("/refresh")
  @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  public String refresh(HttpServletRequest req) {
    return userService.refresh(req.getRemoteUser());
  }
}
