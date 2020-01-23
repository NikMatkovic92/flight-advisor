package com.htec.services.impl;

import com.htec.config.security.JwtUtils;
import com.htec.services.AuthService;
import com.htec.services.dto.security.LoginRequest;
import com.htec.services.dto.security.RegisterRequest;
import com.htec.services.dto.security.TokenResponse;
import com.htec.services.dto.security.UserDetailsImpl;
import com.htec.services.entities.UserEntity;
import com.htec.services.entities.UserRole;

import java.util.Collections;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@Service
@AllArgsConstructor
@Transactional
class AuthServiceImpl implements AuthService {

	private static final String TOKEN_TYPE = "Bearer";

	private final AuthenticationManager authenticationManager;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder encoder;
	private final JwtUtils jwtUtils;

	@Override
	public TokenResponse authenticateUser(final LoginRequest loginRequest) {

		var authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		var jwt = jwtUtils.generateJwtToken(authentication);

		var userDetails = (UserDetailsImpl) authentication.getPrincipal();
		var roles = userDetails.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toList());

		return TokenResponse.builder().type(TOKEN_TYPE).email(userDetails.getEmail()).username(userDetails.getUsername()).token(jwt).roles(roles).id(userDetails.getId())
			.build();
	}

	@Override
	public void registerUser(final RegisterRequest registerRequest) {

		if (userRepository.existsByUsername(registerRequest.getUsername())) {
			throw new BadRequestException("Error: username already exists");
		}

		if (userRepository.existsByEmail(registerRequest.getEmail())) {
			throw new BadRequestException("Error: email already exists");
		}

		var role = registerRequest.getAdmin()? UserRole.ROLE_ADMIN: UserRole.ROLE_USER;

		var user = UserEntity.builder().username(registerRequest.getUsername()).email(registerRequest.getEmail())
			.password(encoder.encode(registerRequest.getPassword())).firstName(registerRequest.getFirstName())
			.roles(Collections.singleton(roleRepository.findByName(role).orElseThrow(() -> new ResourceNotFoundException("Role not found"))))
			.lastName(registerRequest.getLastName()).build();

		userRepository.save(user);
	}
}
