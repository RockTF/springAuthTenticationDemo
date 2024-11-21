package com.altimetrik.ee.demo.service;

import static org.mockito.Mockito.*;

import com.altimetrik.ee.demo.entity.Role;
import com.altimetrik.ee.demo.entity.User;
import com.altimetrik.ee.demo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {
  private static final String TOKEN_RESPONSE = "dummyToken";
  @Mock UserRepository userRepository;
  @InjectMocks UserService userService;
  User user;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    user = new User();
    user.setId(1);
    List<Role> roles = new ArrayList<>();
    roles.add(Role.ROLE_ADMIN);
    user.setRoles(roles);
  }

  @Test
  public void testSearch() throws Exception {
    when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
    User result = userService.search("username");
    Assert.assertEquals(user.getId(), result.getId());
  }
}
