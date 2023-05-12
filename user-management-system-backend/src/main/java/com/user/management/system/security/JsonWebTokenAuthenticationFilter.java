package com.user.management.system.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JsonWebTokenAuthenticationFilter extends RequestHeaderAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(JsonWebTokenAuthenticationFilter.class);

	public JsonWebTokenAuthenticationFilter() {
		// Don't throw exceptions if the header is missing
		this.setExceptionIfHeaderMissing(false);

		// This is the request header it will look for
		this.setPrincipalRequestHeader("Authorization");
	}

	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		super.setAuthenticationManager(authenticationManager);
	}


}
