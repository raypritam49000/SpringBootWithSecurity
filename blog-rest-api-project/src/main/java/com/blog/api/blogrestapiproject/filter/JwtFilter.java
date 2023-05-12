package com.blog.api.blogrestapiproject.filter;

import com.blog.api.blogrestapiproject.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Component
public class JwtFilter extends GenericFilterBean {

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;

        try {
            final String authHeader = request.getHeader("Authorization");

            if ("OPTIONS".equals(request.getMethod())) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
            } else {
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    throw new Exception("Not authenticated!");
                }
            }
            final String token = authHeader.substring(7);

            Claims claims = Jwts.parser()
                    .setSigningKey("Pritam Ray")
                    .parseClaimsJws(token)
                    .getBody();

            if(Objects.isNull(claims)){
                throw new Exception("Token is not valid!");
            }

            request.setAttribute("claims", claims);
            request.setAttribute("blog", servletRequest.getParameter("id"));
            filterChain.doFilter(request, response);
        }
        catch (MalformedJwtException e){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token is not valid!");
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
        }
    }
}