package com.altimetrik.ee.demo.repository;

import com.altimetrik.ee.demo.entity.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);
}
