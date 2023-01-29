package com.user.management.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.user.management.system.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String>{
    public Optional<User> findByEmailId(String email);
    
}
