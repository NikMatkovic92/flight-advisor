package com.htec.services.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.htec.services.CountryService;
import com.htec.services.dto.Country;
import com.htec.services.entities.CountryEntity;

import java.util.Collections;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Nikola Matkovic
 */
@SpringBootTest
class CountryServiceImplTest {

	@Mock
	private CountryRepository countryRepository;

	@InjectMocks
	private CountryServiceImpl countryService;

	@Test
	void findAllCountries() {

		var countryEntity =  mock(CountryEntity.class);

		when(countryEntity.getName()).thenReturn("test country");
		when(countryRepository.findAll()).thenReturn(Collections.singletonList(countryEntity));

		var result = countryService.findAllCountries();

		assertEquals(1, result.size());
	}

	@Test
	void addCountry() {

		var country = Country.builder().name("Serbia").build();

		countryService.addCountry(country);

		verify(countryRepository).save(any());
	}
}