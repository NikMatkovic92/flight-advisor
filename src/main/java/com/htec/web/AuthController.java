package com.htec.web;

import com.htec.services.AuthService;
import com.htec.services.dto.security.LoginRequest;
import com.htec.services.dto.security.RegisterRequest;
import com.htec.services.dto.security.TokenResponse;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/sign-in")
	public TokenResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return authService.authenticateUser(loginRequest);
	}

	@PostMapping("/register")
	public void registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		authService.registerUser(registerRequest);
	}
}
