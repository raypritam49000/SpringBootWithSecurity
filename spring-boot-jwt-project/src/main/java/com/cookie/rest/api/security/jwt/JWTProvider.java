package com.cookie.rest.api.security.jwt;

import com.cookie.rest.api.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JWTProvider {

    String secret = "Pritam Ray";

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String createToken(User user) {
        String jwtToken = Jwts.builder()
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .setSubject(user.getEmail())
                .setId("" + user.getId())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(5l, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return jwtToken;
    }

    public User verifyToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        User user = new User();
        user.setId(Integer.parseInt((String) claims.getId()));
        user.setEmail((String) claims.get("email"));
        user.setUsername((String) claims.get("username"));
        return user;
    }

    public boolean checkTokenExpire(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        if (claims.getExpiration().before(new Date())) {
            return true;
        } else {
            return false;
        }

    }


}