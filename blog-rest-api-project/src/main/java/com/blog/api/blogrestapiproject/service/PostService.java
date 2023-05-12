package com.blog.api.blogrestapiproject.service;

import com.blog.api.blogrestapiproject.model.Post;
import com.blog.api.blogrestapiproject.model.UserPost;

import java.util.List;

public interface PostService {
    public int addPost(Post post);
    public List<Post> getPosts();
    public List<Post> getPostsByCategory(String cat);
    public Post findPostId(long id);
    public UserPost findPostByUser(long postId);
    public int deletePostById(long id, int uid);
    public int updatePostById(Post post);
}
