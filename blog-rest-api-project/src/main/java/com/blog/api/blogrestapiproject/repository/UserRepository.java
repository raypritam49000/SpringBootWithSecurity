package com.blog.api.blogrestapiproject.repository;

import com.blog.api.blogrestapiproject.model.Post;
import com.blog.api.blogrestapiproject.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserRepository {

    @Insert("INSERT INTO users (`username`,`email`,`password`) VALUES (#{username},#{email},#{password})")
    public int createUser(User user);

    @Select("SELECT * FROM users WHERE username = #{username}")
    public User getUserByUsername(String username);

    @Select("SELECT * FROM users WHERE email = #{email} OR username = #{username}")
    public User getUserByUsernameOrEmail(String email, String username);

    @Select("SELECT * FROM users WHERE id = #{id}")
    public User getUserById(int id);

    @Select("SELECT * FROM users")
    public List<User> getUsers();

    @Delete("DELETE FROM users WHERE `id` = #{id}")
    public int deleteUserById(long id);

    @Update("UPDATE users SET `username`=#{username},`email`=#{email},`image`=#{image},`password`=#{password} WHERE `id` = #{id}")
    public int updateUserById(User user);

}
