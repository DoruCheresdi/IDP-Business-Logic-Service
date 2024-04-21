package com.alibou.security.repository;

import java.util.Optional;

import com.alibou.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends JpaRepository<User, Integer>, PagingAndSortingRepository<User, Integer> {

  Optional<User> findByEmail(String email);

}
