package com.htec.services;

import com.htec.services.dto.Country;

import java.util.List;

/**
 * @author Nikola Matkovic
 */
public interface CountryService {

	/**
	 * Returns all countries
	 *
	 * @return countries form db
	 */
	List<Country> findAllCountries();

	/**
	 * Adds new country
	 *
	 * @param country to add
	 */
	void addCountry(Country country);
}
