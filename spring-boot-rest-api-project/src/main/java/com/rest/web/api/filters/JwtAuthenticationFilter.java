package com.rest.web.api.filters;

import com.rest.web.api.dto.UserDto;
import com.rest.web.api.utility.JwtUtils;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Component
public class JwtAuthenticationFilter extends GenericFilterBean {


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
            UserDto user = JwtUtils.getUserFromToken(token);

            if(Objects.isNull(user)){
                throw new Exception("Token is not valid!");
            }

            request.setAttribute("user", user);
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