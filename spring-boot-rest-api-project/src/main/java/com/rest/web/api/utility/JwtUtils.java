package com.rest.web.api.utility;

import com.rest.web.api.dto.UserDto;
import com.rest.web.api.entities.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {

    private static final String SECRET_KEY = "secret";
    private static final long TOKEN_EXPIRATION_TIME = 864000000; // 10 days in milliseconds

    public static String createTaken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        claims.put("email", user.getEmail());
        claims.put("city", user.getCity());
        claims.put("isAdmin", user.getIsAdmin());


        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + TOKEN_EXPIRATION_TIME;
        Date exp = new Date(expMillis);

        return Jwts.builder()
                .setId(user.getId())
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(exp)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public static UserDto getUserFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        String username = (String) claims.get("username");
        String email = (String) claims.get("email");
        String city = (String) claims.get("city");
        String isAdmin = (String) claims.get("isAdmin");
        String id = (String) claims.get("id");
        UserDto userDto = UserDto.builder().id(id).username(username).email(email).city(city).isAdmin(isAdmin).build();
        return userDto;
    }

    public static boolean verifyToken(String token) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(SECRET_KEY);
            Jws<Claims> claimsJws = parser.parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
