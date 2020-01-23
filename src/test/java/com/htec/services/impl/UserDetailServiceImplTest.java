package com.htec.services.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.htec.services.entities.UserEntity;

import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Nikola Matkovic
 */
@SpringBootTest
class UserDetailServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserDetailServiceImpl userDetailService;

	@Test
	void loadUserByUsername() {

		var user = mock(UserEntity.class);

		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

		var result = userDetailService.loadUserByUsername("username");

		Assert.assertNotNull(result);
	}
}