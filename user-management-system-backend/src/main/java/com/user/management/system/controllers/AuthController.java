package com.user.management.system.controllers;

import com.user.management.system.dto.AuthTokenDTO;
import com.user.management.system.dto.UserDTO;
import com.user.management.system.jwt.request.JwtRequest;
import com.user.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public AuthTokenDTO login(@RequestBody JwtRequest jwtRequest){
       return userService.authenticateUser(jwtRequest);
    }

    @PostMapping("/register")
    public UserDTO saveUser(@RequestBody UserDTO userDTO){
        return userService.saveUser(userDTO);
    }
}
