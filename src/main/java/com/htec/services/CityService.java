package com.htec.services;

import com.htec.services.dto.City;

import java.util.List;

/**
 * @author Nikola Matkovic
 */
public interface CityService {

	/**
	 * Returns all the cities from db based on params
	 *
	 * @param numOfComments - number of latest comments for the cities
	 * @param name - name of the city
	 * @return all cities based in search result
	 */
	List<City> findAllCities(Integer numOfComments, String name);

	/**
	 * Adds new city
	 *
	 * @param city - City to add
	 */
	void addCity(City city);
}
