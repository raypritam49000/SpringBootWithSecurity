package com.cookie.rest.api.controllers;

import com.cookie.rest.api.dto.LoginRequest;
import com.cookie.rest.api.entity.User;
import com.cookie.rest.api.security.jwt.JWTProvider;
import com.cookie.rest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JWTProvider jwtProvider;
    private UserService userService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtProvider(JWTProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {

            Optional<User> existingUser = userService.findByUsername(loginRequest.getUsername());

            if(existingUser.isEmpty()){
                return new  ResponseEntity<>(Map.of("status",200,"message","please register first!!!"),HttpStatus.OK);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User user = userService.findByUsername(userDetails.getUsername()).get();
            String jwt = jwtProvider.createToken(user);
            return new  ResponseEntity<>(Map.of("status",200,"message","User has been Login Successfully","token",jwt),HttpStatus.OK);
        } catch (Exception ex) {
            return new  ResponseEntity<>(Map.of("status",501,"message",ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
       try{
             Optional<User> existingUser = userService.findByUsername(user.getUsername());

             if(existingUser.isPresent()){
                 return new  ResponseEntity<>(Map.of("status",409,"message","User Already Register"),HttpStatus.CONFLICT);
             }
             else{
                 User userRegister = userService.createUser(user);
                 return new  ResponseEntity<>(Map.of("status",200,"message","User has been Registered","user",userRegister),HttpStatus.CREATED);
             }
       }
       catch (Exception e){
           return new  ResponseEntity<>(Map.of("status",501,"message",e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

}
