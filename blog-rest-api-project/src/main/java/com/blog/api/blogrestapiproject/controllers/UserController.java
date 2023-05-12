package com.blog.api.blogrestapiproject.controllers;

import com.blog.api.blogrestapiproject.model.User;
import com.blog.api.blogrestapiproject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getUsers() {
        try {
            List<User> users = userService.getUsers();

            if (!users.isEmpty()) {
                return new ResponseEntity<>(users, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        try {
            User user = userService.getUserById(id);

            if (user != null) {
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        try {
            int result = userService.deleteUserById(id);

            if (result > 0) {
                return new ResponseEntity<>("User has been Deleted", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error Occur When User has been Deleted! ", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            int result = userService.updateUserById(user);

            if (result > 0) {
                return new ResponseEntity<>("User has been Updated", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Error Occur When User has been Updated! ", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
