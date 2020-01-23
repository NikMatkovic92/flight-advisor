package com.htec.services.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.htec.services.dto.UploadResult;
import com.htec.services.entities.AirlineEntity;
import com.htec.services.entities.AirportEntity;
import com.htec.services.entities.CityEntity;
import com.htec.services.entities.CountryEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

/**
 * @author Nikola Matkovic
 */
@SpringBootTest
class AirportServiceImplTest {

	@Mock
	private AirportRepository airportRepository;
	@Mock
	private CountryRepository countryRepository;
	@Mock
	private CityRepository cityRepository;
	@InjectMocks
	private AirportServiceImpl airportService;

	@Test
	void importAirportData() throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("files/testAirports.txt");
		MockMultipartFile multipartFile = new MockMultipartFile("file", inputStream);

		var mockCity = mock(CityEntity.class);
		var mockCountry = mock(CountryEntity.class);

		when(cityRepository.findByName(anyString())).thenReturn(Collections.singletonList(mockCity));
		when(countryRepository.findByName(anyString())).thenReturn(mockCountry);
		when(mockCity.getCountryEntity()).thenReturn(mockCountry);
		when(mockCountry.getName()).thenReturn("test country");

		var result = airportService.importAirportData(multipartFile);

		verify(airportRepository, times(2)).save(any());
		assertEquals(0, result.getFailedLinesOnImport().size());
	}
}