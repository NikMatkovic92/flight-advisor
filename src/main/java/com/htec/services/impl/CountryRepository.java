package com.htec.services.impl;

import com.htec.services.entities.CountryEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Nikola Matkovic
 */
interface CountryRepository extends JpaRepository<CountryEntity, Long> {

	CountryEntity findByName(final String name);
}
