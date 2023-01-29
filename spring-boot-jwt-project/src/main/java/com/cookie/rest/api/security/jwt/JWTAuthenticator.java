//package com.cookie.rest.api.security;
//
//import com.cookie.rest.api.entity.User;
//import io.dropwizard.auth.AuthenticationException;
//import io.dropwizard.auth.Authenticator;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Optional;
//
//public class JWTAuthenticator implements Authenticator<String, User> {
//
//    private JWTProvider jwtProvider;
//
//    @Autowired
//    public void setJwtProvider(JWTProvider jwtProvider) {
//        this.jwtProvider = jwtProvider;
//    }
//
//    @Override
//    public Optional<User> authenticate(String token) throws AuthenticationException {
//        // Verify the JWT and return the associated user
//        try {
//            User user = jwtProvider.verifyToken(token);
//            return Optional.of(user);
//        } catch (Exception e) {
//            return Optional.empty();
//        }
//    }
//}