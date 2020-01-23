package com.htec.services.impl;

import com.htec.services.entities.AirlineEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Nikola Matkovic
 */
interface AirlineRepository extends JpaRepository<AirlineEntity, Long> {

	@Query("from AirlineEntity as air where air.iataAirlineCode = :code or air.icaoAirlineCode = :code")
	AirlineEntity findByIataAirlineCodeOrIcaoAirlineCode(@Param("code") String code);
}
