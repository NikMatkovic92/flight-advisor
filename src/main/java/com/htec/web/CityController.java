package com.htec.web;

import com.htec.services.CityService;
import com.htec.services.dto.City;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@RestController
@RequestMapping("/api/cities")
@AllArgsConstructor
public class CityController {

	private final CityService cityService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<City> getAllCities(@RequestParam(name = "cityName", required = false) final String cityName,
		@RequestParam(name = "numberOfComments", required = false) final Integer numberOfComments) {
		return cityService.findAllCities(numberOfComments, cityName);
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public void addCity(@Valid  @RequestBody City city) {
		cityService.addCity(city);
	}

}
