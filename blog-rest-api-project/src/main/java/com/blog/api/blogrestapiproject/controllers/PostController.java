package com.blog.api.blogrestapiproject.controllers;

import com.blog.api.blogrestapiproject.model.Post;
import com.blog.api.blogrestapiproject.model.UserPost;
import com.blog.api.blogrestapiproject.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/")
    public ResponseEntity<?> addPost(@RequestBody Post post) {
        try {
            int result = postService.addPost(post);

            if (result > 0) {
                return new ResponseEntity<>("Post has been created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Fail Post Created", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getPosts() {
        try {
            List<Post> posts = postService.getPosts();

            if (posts != null && !posts.isEmpty()) {
                return new ResponseEntity<>(posts, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(posts, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> findPostByUser(@PathVariable("postId") long postId) {
        try {
            UserPost userPost = postService.findPostByUser(postId);

            if (userPost != null) {
                return new ResponseEntity<>(userPost, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Post Not Found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}/{uid}")
    public ResponseEntity<?> deletePostById(@PathVariable("id") long id, @PathVariable("uid") int uid) {
        try {
            int isDeleted = postService.deletePostById(id, uid);

            if (isDeleted > 0) {
                return new ResponseEntity<>("Post has been deleted!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You can delete only your post!", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> updatePostById(@RequestBody Post post) {
        try {
            int result = postService.updatePostById(post);

            if (result > 0) {
                return new ResponseEntity<>("Post has been updated.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Fail Post Updated", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
