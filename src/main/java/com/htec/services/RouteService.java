package com.htec.services;

import com.htec.services.dto.FlightRoute;
import com.htec.services.dto.UploadResult;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Nikola Matkovic
 */
public interface RouteService {

	/**
	 * Inserts Route data into database based on file
	 *
	 * @param file - file for import
	 * @return lines that have failed on import
	 */
	UploadResult uploadRoute(MultipartFile file);

	/**
	 * Returns the cheapest route based on the source and destination city
	 *
	 * @param sourceCity - starting city
	 * @param destinationCity - destination city of a route
	 * @return flight route that also specifies the price and the duration of the flight
	 */
	List<FlightRoute> getRoute(String sourceCity, String destinationCity);
}
