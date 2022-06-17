package com.spring.crud.jwt_security.repository;

import com.spring.crud.jwt_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);
    boolean existsByEmail(String email);
}
