package com.htec.web;

import com.htec.services.CountryService;
import com.htec.services.dto.Country;

import java.util.List;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@RestController
@RequestMapping("/api/countries")
@AllArgsConstructor
public class CountryController {

	private final CountryService countryService;

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<Country> getAllCountries() {
		return countryService.findAllCountries();
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public void addCountry(@Valid @RequestBody final Country country) {
		countryService.addCountry(country);
	}
}
