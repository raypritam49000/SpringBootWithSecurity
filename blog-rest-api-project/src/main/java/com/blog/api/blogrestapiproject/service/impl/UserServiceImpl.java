package com.blog.api.blogrestapiproject.service.impl;

import com.blog.api.blogrestapiproject.model.User;
import com.blog.api.blogrestapiproject.repository.UserRepository;
import com.blog.api.blogrestapiproject.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public int createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    @Override
    public User getUserByUsernameOrEmail(String email, String username) {
        return userRepository.getUserByUsernameOrEmail(email,username);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public int deleteUserById(long id) {
        return userRepository.deleteUserById(id);
    }

    @Override
    public int updateUserById(User user) {
        return userRepository.updateUserById(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }
}
