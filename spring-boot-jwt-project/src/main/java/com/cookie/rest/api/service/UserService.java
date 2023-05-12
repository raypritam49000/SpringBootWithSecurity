package com.cookie.rest.api.service;

import com.cookie.rest.api.entity.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> findByUsername(String username);
    public User createUser(User user);
}
