package com.rest.web.api.controllers;

import com.rest.web.api.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public String hello(HttpServletRequest request){
        return "Hello Pritam Ray";
    }

    @GetMapping("/hello")
    public String hellog(HttpServletRequest request){
        return "Hello G Pritam Ray";
    }
}
