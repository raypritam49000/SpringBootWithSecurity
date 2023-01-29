package com.user.management.system.security;

import com.user.management.system.dto.AuthTokenDetailsDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class JsonWebTokenUtility {
	private static final Logger logger = LoggerFactory.getLogger(JsonWebTokenUtility.class);

	private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

	public JsonWebTokenUtility() {
	}

	public static String createJsonWebToken(AuthTokenDetailsDTO authTokenDetailsDTO) {
		logger.info("createJsonWebToken()...");

		return Jwts.builder()
				.setSubject(authTokenDetailsDTO.id)
				.claim("firstName", authTokenDetailsDTO.firstName)
				.claim("emailId", authTokenDetailsDTO.emailId)
				.claim("lastName", authTokenDetailsDTO.lastName)
				.setExpiration(authTokenDetailsDTO.expirationDate)
				.signWith(signatureAlgorithm, getSecretKey()).compact();
	}

	private static Key deserializeKey(String encodedKey) {
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		return new SecretKeySpec(decodedKey, signatureAlgorithm.getJcaName());
	}

	private static Key getSecretKey() {
		String encodedKey = "L7A/6zARSkK1j7Vd5SDD9pSSqZlqF7mAhiOgRbgv9Smce6tf4cJnvKOjtKPxNNnWQj+2lQEScm3XIUjhW+YVZg==";
		return deserializeKey(encodedKey);
	}

	public static AuthTokenDetailsDTO parseAndValidate(String token) {
		Claims claims = parseTokenIntoClaims(token);

		if (claims == null) {
			return null;
		}

		return createTokenDTOFromClaims(claims);
	}

	private static Claims parseTokenIntoClaims(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(getSecretKey()).parseClaimsJws(token).getBody();
		} catch (ExpiredJwtException expiredJwtException) {
			logger.error("Failed to validate token. Token has expired.");
			return null;
		} catch (Exception ex) {
			logger.error("parseAndValidate() ... EXCEPTION" + ex.getMessage());
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized", ex);
		}

		return claims;
	}

	private static AuthTokenDetailsDTO createTokenDTOFromClaims(Claims claims) {

		String id = claims.getSubject();
		String firstName = (String) claims.get("firstName");
		String lastName = (String) claims.get("lastName");
		String emailId = (String) claims.get("emailId");
		Date expirationDate = claims.getExpiration();

		AuthTokenDetailsDTO authTokenDetailsDTO = new AuthTokenDetailsDTO();
        authTokenDetailsDTO.setId(id);
		authTokenDetailsDTO.setEmailId(emailId);
		authTokenDetailsDTO.setFirstName(firstName);
		authTokenDetailsDTO.setLastName(lastName);
		authTokenDetailsDTO.setExpirationDate(expirationDate);

		return authTokenDetailsDTO;
	}

	private static String serializeKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public static Date buildExpirationDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 4);
		return calendar.getTime();
	}

}