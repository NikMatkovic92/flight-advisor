package com.htec.services;

import com.htec.services.dto.security.LoginRequest;
import com.htec.services.dto.security.RegisterRequest;
import com.htec.services.dto.security.TokenResponse;

/**
 * @author Nikola Matkovic
 */
public interface AuthService {

	/**
	 * Authenticates the user and returns the token to be used in further interactions with the api
	 *
	 * @param loginRequest - request for log in (username and password)
	 * @return token details and value
	 */
	TokenResponse authenticateUser(LoginRequest loginRequest);

	/**
	 * Registers the user
	 *
	 * @param registerRequest - request for registering the user, if flag for admin is false, the registered user is assigned the role of ROLE_USER
	 */
	void registerUser(RegisterRequest registerRequest);
}
