package com.htec.services.impl;

import com.htec.services.CountryService;
import com.htec.services.dto.Country;
import com.htec.services.entities.CountryEntity;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@Service
@AllArgsConstructor
@Transactional
class CountryServiceImpl implements CountryService {

	private final CountryRepository countryRepository;

	@Override
	public List<Country> findAllCountries() {
		return countryRepository.findAll().stream().map(countryEntity -> Country.builder().name(countryEntity.getName()).build()).collect(Collectors.toList());
	}

	@Override
	public void addCountry(final Country country) {
		countryRepository.save(CountryEntity.builder().name(country.getName()).build());
	}
}
