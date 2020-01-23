package com.htec.services.impl;

import com.htec.services.entities.AirportEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Nikola Matkovic
 */
interface AirportRepository extends JpaRepository<AirportEntity, Long> {

	@Query("from AirportEntity as air where air.iataAirportCode = :code or air.icaoAirportcode = :code")
	AirportEntity findByIataAirportCodeOrIcaoAirportCode(@Param("code") String code);
}
