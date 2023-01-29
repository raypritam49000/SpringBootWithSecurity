package com.cookie.security.rest.api.service;

import com.cookie.security.rest.api.dto.UserDTO;


public interface UserService {
    public UserDTO findByUsername(String username);

    public UserDTO createUser(UserDTO userDTO);
}
