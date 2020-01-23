package com.htec.services.impl;

import com.htec.services.entities.RouteEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Nikola Matkovic
 */
interface RouteRepository extends JpaRepository<RouteEntity, Long> {


	@Query("from RouteEntity as route where route.sourceAirport.cityEntity.name = :sourceCity and route.destinationAirport.cityEntity.name = :destinationCity")
	List<RouteEntity> findRouteForCities(@Param("sourceCity") String sourceCity, @Param("destinationCity") String destinationCity);
}
