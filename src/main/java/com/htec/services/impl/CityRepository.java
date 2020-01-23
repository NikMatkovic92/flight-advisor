package com.htec.services.impl;

import com.htec.services.entities.CityEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikola Matkovic
 */
interface CityRepository extends JpaRepository<CityEntity, Long> {

	List<CityEntity> findByName(final String name);
}
