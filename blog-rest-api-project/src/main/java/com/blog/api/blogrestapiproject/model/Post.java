package com.blog.api.blogrestapiproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int id;
    private String title;
    private String desc;
    private String img;
    private Date date;
    private int uid;
    private String cat;
}
