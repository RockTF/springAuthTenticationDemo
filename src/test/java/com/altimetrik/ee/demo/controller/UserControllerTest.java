package com.altimetrik.ee.demo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.altimetrik.ee.demo.dto.UserResponseDTO;
import com.altimetrik.ee.demo.entity.Role;
import com.altimetrik.ee.demo.entity.User;
import com.altimetrik.ee.demo.service.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserService userService;

  @MockBean private ModelMapper modelMapper;

    @BeforeEach
  void setUp() {
        User mockUser = new User();
    mockUser.setUsername("testUser");
    mockUser.setEmail("test.user@example.com");

        UserResponseDTO mockUserResponseDTO = new UserResponseDTO();
    mockUserResponseDTO.setId(1);
    mockUserResponseDTO.setUsername("testUser");
    mockUserResponseDTO.setEmail("test.user@example.com");
    mockUserResponseDTO.setRoles(List.of(Role.ROLE_ADMIN, Role.ROLE_USER));

    when(userService.search("testUser")).thenReturn(mockUser);
    when(modelMapper.map(mockUser, UserResponseDTO.class)).thenReturn(mockUserResponseDTO);
  }

  @Test
  @WithMockUser(
      username = "admin",
      roles = {"ADMIN"})
  void shouldReturnUserForAdmin() throws Exception {
    mockMvc
        .perform(get("/users/testUser"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.username", is("testUser")))
        .andExpect(jsonPath("$.email", is("test.user@example.com")))
        .andExpect(jsonPath("$.roles", hasSize(2)))
        .andExpect(jsonPath("$.roles[0]", is("ROLE_ADMIN")))
        .andExpect(jsonPath("$.roles[1]", is("ROLE_USER")));
  }

  @Test
  @WithMockUser(
      username = "user",
      roles = {"USER"})
  void shouldReturnUserForRegularUser() throws Exception {
    mockMvc
        .perform(get("/users/testUser"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", is(1)))
        .andExpect(jsonPath("$.username", is("testUser")))
        .andExpect(jsonPath("$.email", is("test.user@example.com")))
        .andExpect(jsonPath("$.roles", hasSize(2)))
        .andExpect(jsonPath("$.roles[0]", is("ROLE_ADMIN")))
        .andExpect(jsonPath("$.roles[1]", is("ROLE_USER")));
  }

  @Test
  void shouldDenyAccessForUnauthenticatedUser() throws Exception {
    mockMvc.perform(get("/users/testUser")).andExpect(status().isUnauthorized());
  }
}
