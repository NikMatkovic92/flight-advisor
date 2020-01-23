package com.htec.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.htec.services.dto.City;
import com.htec.services.dto.Country;
import com.htec.services.entities.CityEntity;
import com.htec.services.entities.CommentEntity;
import com.htec.services.entities.CountryEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Nikola Matkovic, n.matkovic@levi9.com
 */
@SpringBootTest
class CityServiceImplTest {

	@Mock
	private CityRepository cityRepository;

	@Mock
	private CountryRepository countryRepository;

	@InjectMocks
	private CityServiceImpl cityService;

	@Test
	void findAllCitiesNoName() {
		var cityEntity1 = setupComment(3, "City1");
		var cityEntity2 = setupComment(2, "City2");
		var cities = Arrays.asList(cityEntity1, cityEntity2);

		when(cityRepository.findAll()).thenReturn(cities);

		var result = cityService.findAllCities(null, null);

		Assert.assertEquals(2, result.size());
		Assert.assertEquals(3, result.get(0).getComments().size());
		Assert.assertEquals(2, result.get(1).getComments().size());
	}

	@Test
	void findAllCitiesWithName() {
		var cityEntity1 = setupComment(3, "City1");

		when(cityRepository.findByName("City1")).thenReturn(Collections.singletonList(cityEntity1));

		var result = cityService.findAllCities(2, "City1");

		Assert.assertEquals(1, result.size());
		Assert.assertEquals(2, result.get(0).getComments().size());
	}

	@Test
	void addCity() {

		var countryEntity = mock(CountryEntity.class);
		var city = City.builder().name("Some name").country(Country.builder().name("some country name").build()).description("desc").build();

		when(countryRepository.findByName(anyString())).thenReturn(countryEntity);

		cityService.addCity(city);

		verify(cityRepository).save(any());

	}

	private CityEntity setupComment(final int numOfComments, final String name) {
		Set<CommentEntity> commentEntityList = new HashSet<>();
		for (int i = 1; i <= numOfComments; i++) {
			var comment = CommentEntity.builder().id((long) i).createdAt(LocalDateTime.now()).text("some text").build();
			commentEntityList.add(comment);
		}

		return CityEntity.builder().commentEntities(commentEntityList).countryEntity(CountryEntity.builder().name("some country").build())
			.description("test desc").name(name).build();
	}
}