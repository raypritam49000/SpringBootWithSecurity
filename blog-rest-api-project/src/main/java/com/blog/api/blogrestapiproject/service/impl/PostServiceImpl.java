package com.blog.api.blogrestapiproject.service.impl;

import com.blog.api.blogrestapiproject.model.Post;
import com.blog.api.blogrestapiproject.model.UserPost;
import com.blog.api.blogrestapiproject.repository.PostRepository;
import com.blog.api.blogrestapiproject.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public int addPost(Post post) {
        return postRepository.addPost(post);
    }

    @Override
    public List<Post> getPosts() {
        return postRepository.getPosts();
    }

    @Override
    public List<Post> getPostsByCategory(String cat) {
        return postRepository.getPostsByCategory(cat);
    }

    @Override
    public Post findPostId(long id) {
        return postRepository.findPostId(id);
    }

    @Override
    public UserPost findPostByUser(long postId) {
        return postRepository.findPostByUser(postId);
    }

    @Override
    public int deletePostById(long id, int uid) {
        return postRepository.deletePostById(id,uid);
    }

    @Override
    public int updatePostById(Post post) {
        return postRepository.updatePostById(post);
    }
}
