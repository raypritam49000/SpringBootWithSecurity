package com.blog.api.blogrestapiproject.service;

import com.blog.api.blogrestapiproject.model.User;

import java.util.List;

public interface UserService {
    public int createUser(User user);
    public User getUserByUsername(String username);
    public User getUserByUsernameOrEmail(String email,String username);
    public List<User> getUsers();
    public int deleteUserById(long id);

    public int updateUserById(User user);

    public User getUserById(int id);
}
