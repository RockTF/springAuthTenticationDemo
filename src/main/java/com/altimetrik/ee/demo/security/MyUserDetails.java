package com.altimetrik.ee.demo.security;

import com.altimetrik.ee.demo.entity.User;
import com.altimetrik.ee.demo.exception.CustomException;
import com.altimetrik.ee.demo.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetails implements UserDetailsService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    final User user =
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND));

    if (user == null) {
      throw new UsernameNotFoundException("User '" + username + "' not found");
    }

    List<GrantedAuthority> authorities =
        user.getRoles().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
            .collect(Collectors.toList());
    return new org.springframework.security.core.userdetails.User(
        user.getUsername(), user.getPassword(), authorities);
  }
}
