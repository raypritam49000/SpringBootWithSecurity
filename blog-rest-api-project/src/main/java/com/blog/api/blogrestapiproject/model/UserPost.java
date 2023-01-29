package com.blog.api.blogrestapiproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserPost {
    private int id;
    private String username;
    private String title;
    private String desc;
    private String img;
    private String image;
    private String cat;
    private Date date;
}
