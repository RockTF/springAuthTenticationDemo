package com.altimetrik.ee.demo.service;

import com.altimetrik.ee.demo.entity.Role;
import com.altimetrik.ee.demo.entity.User;
import com.altimetrik.ee.demo.repository.UserRepository;
import com.altimetrik.ee.demo.security.JwtTokenProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    private static final String TOKEN_RESPONSE = "dummyToken";
    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    @Mock
    AuthenticationManager authenticationManager;
    @InjectMocks
    UserService userService;
    User user;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setId(1);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_ADMIN);
        user.setRoles(roles);
        when(jwtTokenProvider.createToken(anyString(), any())).thenReturn(TOKEN_RESPONSE);
    }

    @Test
    public void testSignIn() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        String result = userService.signIn("username", "password");
        Assert.assertEquals(TOKEN_RESPONSE, result);
    }

    @Test
    public void testSignUp() throws Exception {
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(new User());
        user.setUsername("test");
        String result = userService.signUp(user);
        Assert.assertEquals(TOKEN_RESPONSE, result);
    }

    @Test
    public void testSearch() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        User result = userService.search("username");
        Assert.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testFindCurrentUser() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.ofNullable(user));
        when(jwtTokenProvider.getUsername(anyString())).thenReturn("getUsernameResponse");
        when(jwtTokenProvider.resolveToken(any())).thenReturn("resolveTokenResponse");
        User result = userService.findCurrentUser(null);
        Assert.assertEquals(user.getId(), result.getId());
    }

    @Test
    public void testRefresh() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(new User()));
        when(jwtTokenProvider.createToken(anyString(), any())).thenReturn(TOKEN_RESPONSE);
        String result = userService.refresh("username");
        Assert.assertEquals(TOKEN_RESPONSE, result);
    }
}
