package com.altimetrik.ee.demo.repository;

import com.altimetrik.ee.demo.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);
}
