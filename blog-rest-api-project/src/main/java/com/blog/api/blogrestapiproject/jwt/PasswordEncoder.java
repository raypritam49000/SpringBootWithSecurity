package com.blog.api.blogrestapiproject.jwt;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean matchPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
