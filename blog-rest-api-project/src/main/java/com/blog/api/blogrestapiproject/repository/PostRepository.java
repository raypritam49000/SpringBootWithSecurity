package com.blog.api.blogrestapiproject.repository;

import com.blog.api.blogrestapiproject.model.Post;
import com.blog.api.blogrestapiproject.model.UserPost;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostRepository {

    @Insert("INSERT INTO posts(`title`, `desc`, `img`, `cat`,`uid`) VALUES (#{title}, #{desc}, #{img}, #{cat},#{uid})")
    public int addPost(Post post);

    @Select("select * from posts")
    public List<Post> getPosts();

    @Select("select * from posts where cat = #{cat}")
    public List<Post> getPostsByCategory(String cat);

    @Select("SELECT * FROM posts WHERE id = #{id}")
    public Post findPostId(long id);

    @Select("SELECT p.id,`username`,`title`, `desc`, `img`, `image`, `cat`,`date` FROM users u JOIN posts p ON u.id = p.uid WHERE p.id = #{postId}")
    public UserPost findPostByUser(long postId);

    @Delete("DELETE FROM posts WHERE `id` = #{id} AND `uid` = #{uid}")
    public int deletePostById(long id, int uid);

    @Update("UPDATE posts SET `title`=#{title},`desc`=#{desc},`img`=#{img},`cat`=#{cat} WHERE `id` = #{id} AND `uid` = #{uid}")
    public int updatePostById(Post post);
}
