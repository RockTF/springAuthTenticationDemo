package com.altimetrik.ee.demo.service;

import com.altimetrik.ee.demo.entity.User;
import com.altimetrik.ee.demo.exception.CustomException;
import com.altimetrik.ee.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  public User search(String username) {
    User user = getUserByName(username);
    if (user == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return user;
  }

  private User getUserByName(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND));
  }
}
