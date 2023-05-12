package com.cookie.security.rest.api.repository;

import com.cookie.security.rest.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    public Optional<User> findByUsername(String username);
}
