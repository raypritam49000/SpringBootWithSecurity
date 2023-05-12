package com.cookie.security.rest.api.controllers;

import com.cookie.security.rest.api.dto.UserDTO;
import com.cookie.security.rest.api.security.JwtUtil;
import com.cookie.security.rest.api.service.UserService;
import com.cookie.security.rest.api.utility.AppUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(HttpServletResponse response, @RequestBody UserDTO userDTO) {
        try {
            UserDTO existingUser = userService.findByUsername(userDTO.getUsername());
            if (ObjectUtils.isEmpty(existingUser)) {
                return new ResponseEntity<>(Map.of("status", 200, "message", "please register first!!!"), HttpStatus.OK);
            } else {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
                final UserDetails userDetails = userDetailsService.loadUserByUsername(userDTO.getUsername());
                final String token = jwtUtil.generateToken(userDetails);
                UserDTO loginUser = userService.findByUsername(userDetails.getUsername());
                AppUtility.setJwtCookie(response, token);
                return new ResponseEntity<>(Map.of("status", 200, "message", "User has been Login Successfully","user",loginUser), HttpStatus.OK);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(Map.of("status", 501, "message", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            UserDTO existingUser = userService.findByUsername(userDTO.getUsername());
            if (!ObjectUtils.isEmpty(existingUser)) {
                return new  ResponseEntity<>(Map.of("status",409,"message","User Already Register"),HttpStatus.CONFLICT);
            }
            UserDTO userRegister = userService.createUser(userDTO);
            return new ResponseEntity<>(Map.of("status", 200, "message", "User has been Registered", "user", userRegister), HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(Map.of("status", 501, "message", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return ResponseEntity.ok().body(Map.of("message", "User Logout", "status", 200));
    }
}
