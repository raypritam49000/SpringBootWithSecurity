package com.cookie.security.rest.api.utility;

import org.springframework.http.HttpHeaders;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class AppUtility {

    public static void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
        cookie.setPath("/");
        response.addCookie(cookie);

        // Set the JWT token in a cookie
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, "jwt=" + cookie.getValue() + "; Path=/; HttpOnly; SameSite=Strict");
    }

}
