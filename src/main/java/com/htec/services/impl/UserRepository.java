package com.htec.services.impl;

import com.htec.services.entities.UserEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikola Matkovic
 */
interface UserRepository extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
