package com.altimetrik.ee.demo.service;

import com.altimetrik.ee.demo.entity.User;
import com.altimetrik.ee.demo.exception.CustomException;
import com.altimetrik.ee.demo.repository.UserRepository;
import com.altimetrik.ee.demo.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @Autowired private AuthenticationManager authenticationManager;

  public String signIn(String username, String password) {
    try {
      User user = getUserByName(username);
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password));
      return jwtTokenProvider.createToken(username, user.getRoles());
    } catch (AuthenticationException e) {
      throw new CustomException(
          "Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String signUp(User user) {
    if (!userRepository.existsByUsername(user.getUsername())) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      return jwtTokenProvider.createToken(user.getUsername(), user.getRoles());
    } else {
      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public User search(String username) {
    User user = getUserByName(username);
    if (user == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return user;
  }

  public User findCurrentUser(HttpServletRequest req) {
    return userRepository
        .findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)))
        .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND));
  }

  public String refresh(String username) {
    User user = getUserByName(username);
    return jwtTokenProvider.createToken(username, user.getRoles());
  }

  private User getUserByName(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND));
  }
}
