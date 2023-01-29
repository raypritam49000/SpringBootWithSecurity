//package com.cookie.rest.api.security.jwt.filters;
//
//import io.dropwizard.auth.AuthFilter;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtParser;
//import io.jsonwebtoken.Jwts;
//import org.springframework.context.annotation.Configuration;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.core.Cookie;
//import javax.ws.rs.core.Response;
//
//@Configuration
//public class JwtAuthFilter extends AuthFilter {
//
//    private JwtCookieHandler jwtCookieHandler;
//
//    public JwtAuthFilter(JwtCookieHandler jwtCookieHandler) {
//        this.jwtCookieHandler = jwtCookieHandler;
//    }
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) {
//        Cookie jwtCookie = jwtCookieHandler.getCookie(requestContext);
//        if (jwtCookie != null) {
//            String jwt = jwtCookie.getValue();
//            if (jwt != null) {
//                try {
//                    JwtParser jwtParser = Jwts.parser().setSigningKey("secret");
//                    Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
//                    requestContext.se;
//                    requestContext.setSecurityContext(new JwtSecurityContext(claims));
//                } catch (Exception e) {
//                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//                }
//            } else {
//                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//            }
//        } else {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
//        }
//    }
//}