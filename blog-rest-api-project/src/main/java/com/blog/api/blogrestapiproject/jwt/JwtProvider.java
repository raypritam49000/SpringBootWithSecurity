package com.blog.api.blogrestapiproject.jwt;

import com.blog.api.blogrestapiproject.model.User;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jwts;

@Service
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${app.jwttoken.message}")
    private String message;

    public Map<String, String> generateToken(User user) {
        String jwtToken = "";
        jwtToken = Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("message", message);
        return jwtTokenGen;
    }

}