package com.blog.api.blogrestapiproject.controllers;

import com.blog.api.blogrestapiproject.jwt.JwtProvider;
import com.blog.api.blogrestapiproject.jwt.PasswordEncoder;
import com.blog.api.blogrestapiproject.model.User;
import com.blog.api.blogrestapiproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            User existingUser = userService.getUserByUsername(user.getUsername());

            if (existingUser != null) {
                return new ResponseEntity<>("User Already Exists", HttpStatus.CONFLICT);
            } else {
                user.setPassword(PasswordEncoder.hashPassword(user.getPassword()));
                userService.createUser(user);
                return new ResponseEntity<>("User has been created", HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User existingUser = userService.getUserByUsername(user.getUsername());
            if (existingUser != null) {

                boolean isMatchPassword = PasswordEncoder.matchPassword(user.getPassword(),existingUser.getPassword());

                if(isMatchPassword){
                    Map<String, String> apiResponse = jwtProvider.generateToken(existingUser);
                    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
                }
                else{
                    return new ResponseEntity<>("Wrong username or password!", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
