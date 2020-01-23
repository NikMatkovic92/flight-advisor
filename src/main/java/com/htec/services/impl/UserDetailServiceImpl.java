package com.htec.services.impl;

import com.htec.services.dto.security.UserDetailsImpl;
import com.htec.services.entities.UserEntity;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@Service
@AllArgsConstructor
@Primary
class UserDetailServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}
}
