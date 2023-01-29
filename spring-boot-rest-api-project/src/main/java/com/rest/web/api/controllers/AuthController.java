package com.rest.web.api.controllers;

import com.rest.web.api.dto.UserDto;
import com.rest.web.api.entities.User;
import com.rest.web.api.service.IUserService;
import com.rest.web.api.utility.JwtUtils;
import com.rest.web.api.utility.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        try {
            Boolean isExistingUser = userService.existsByUsername(userDto.getUsername());

            if (isExistingUser) {
                return new ResponseEntity<>("User Already Exists", HttpStatus.CONFLICT);
            }
            userService.createUser(userDto);
            return new ResponseEntity<>("User has been created", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDTO) {
        try {
            Optional<UserDto> existingUser = userService.findByUsername(userDTO.getUsername());

            if (existingUser.isPresent()) {
                boolean isMatchPassword = PasswordEncoder.matchPassword(userDTO.getPassword(), existingUser.get().getPassword());
                if (isMatchPassword) {
                    User user = User.builder().id(existingUser.get().getId()).email(existingUser.get().getEmail()).username(existingUser.get().getUsername()).password(existingUser.get().getPassword()).city(existingUser.get().getCity()).isAdmin(existingUser.get().getIsAdmin()).build();
                    String token = JwtUtils.createTaken(user);
                    return new ResponseEntity<>(Map.of("message","User Login Successfully","status",200,"token",token), HttpStatus.OK);
                } else {
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
