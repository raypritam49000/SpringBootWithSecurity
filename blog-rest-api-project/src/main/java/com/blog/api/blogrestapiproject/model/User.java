package com.blog.api.blogrestapiproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
}
