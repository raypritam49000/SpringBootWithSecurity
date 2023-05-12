package com.blog.api.blogrestapiproject.config;

import com.blog.api.blogrestapiproject.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new JwtFilter());
        // provide endpoints which needs to be restricted.
        // All Endpoints would be restricted if unspecified
        filter.addUrlPatterns("/api/posts/*");
        filter.addUrlPatterns("/api/users/*");
        return filter;
    }
}
