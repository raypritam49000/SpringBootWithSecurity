package com.blog.api.blogrestapiproject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BlogRestApiProjectApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BlogRestApiProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List list1 = List.of("admin","user","manager","dba","hr","tester");
        List list2 = List.of("tester","ui developer");

        Boolean anyMatch = list1.stream().anyMatch(list2::contains);

        System.out.println(anyMatch);
    }
}
