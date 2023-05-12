package com.user.management.system.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.user.management.system.dto.AuthTokenDTO;
import com.user.management.system.dto.UserDTO;
import com.user.management.system.jwt.request.JwtRequest;

public interface UserService {
    public UserDTO saveUser(UserDTO userDTO);
    public UserDTO updateUser(String id,UserDTO userDTO);   
    public List<UserDTO> getUsers();
    public UserDTO getUserById(String id);
    public Boolean deleteUserById(String id);

    public AuthTokenDTO authenticateUser(JwtRequest jwtRequest);

    public ByteArrayInputStream load();
}
