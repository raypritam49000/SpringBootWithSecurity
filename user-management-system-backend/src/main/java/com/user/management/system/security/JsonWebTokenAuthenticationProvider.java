package com.user.management.system.security;

import com.user.management.system.dto.AuthTokenDetailsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Component
public class JsonWebTokenAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(JsonWebTokenAuthenticationProvider.class);


	private JsonWebTokenUtility tokenService = new JsonWebTokenUtility();

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Authentication authenticatedUser = null;
		// Only process the PreAuthenticatedAuthenticationToken
		if (authentication.getClass().isAssignableFrom(PreAuthenticatedAuthenticationToken.class)
				&& authentication.getPrincipal() != null) {
			String tokenHeader = (String) authentication.getPrincipal();
			UserDetails userDetails = null;
			try {
				userDetails = parseToken(tokenHeader);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userDetails != null) {
				authenticatedUser = new JsonWebTokenAuthentication(userDetails, tokenHeader);
			}
		} else {
			// It is already a JsonWebTokenAuthentication
			authenticatedUser = authentication;
		}
		return authenticatedUser;
	}

	private UserDetails parseToken(String tokenHeader) {
		UserDetails principal = null;
		AuthTokenDetailsDTO authTokenDetails = tokenService.parseAndValidate(tokenHeader);

		if (authTokenDetails != null) {
			principal = new User(authTokenDetails.getEmailId(), "", Arrays.asList());
		}

		return principal;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(PreAuthenticatedAuthenticationToken.class)
				|| authentication.isAssignableFrom(JsonWebTokenAuthentication.class);
	}

}
