package com.htec.services.impl;

import com.htec.services.entities.RoleEntity;
import com.htec.services.entities.UserRole;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikola Matkovic
 */
interface RoleRepository extends JpaRepository<RoleEntity, Long> {
	Optional<RoleEntity> findByName(UserRole name);
}
