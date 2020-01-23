package com.htec.services.impl;

import com.htec.services.CityService;
import com.htec.services.dto.City;
import com.htec.services.dto.Comment;
import com.htec.services.dto.Country;
import com.htec.services.entities.CityEntity;
import com.htec.services.entities.CommentEntity;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@Service
@AllArgsConstructor
@Transactional
class CityServiceImpl implements CityService {

	private final CityRepository cityRepository;
	private final CountryRepository countryRepository;

	@Override
	public List<City> findAllCities(final Integer numOfComments, final String name) {
		final var cityEntity = name != null ? cityRepository.findByName(name) : cityRepository.findAll();
		return cityEntity.stream().map(cEntity -> getCity(cEntity, numOfComments)).collect(Collectors.toList());
	}

	@Override
	public void addCity(final City city) {
		var countryEntity = countryRepository.findByName(city.getCountry().getName());

		if (countryEntity == null) {
			throw new BadRequestException("valid country must be provided");
		}

		cityRepository.save(CityEntity.builder()
			.countryEntity(countryEntity)
			.name(city.getName())
			.description(city.getDescription())
			.build());
	}

	private City getCity(final CityEntity cityEntity, final Integer numOfComments) {

		Stream<CommentEntity> commentEntityStream = numOfComments == null ? cityEntity.getCommentEntities().stream()
			.sorted(Comparator.comparing(CommentEntity::getCreatedAt).reversed()) :
			cityEntity.getCommentEntities().stream()
				.sorted(Comparator.comparing(CommentEntity::getCreatedAt).reversed()).limit(numOfComments);

		return City.builder()
			.comments(commentEntityStream.map(commentEntity -> Comment.builder().id(commentEntity.getId())
				.text(commentEntity.getText()).build()).collect(
				Collectors.toSet()))
			.description(cityEntity.getDescription())
			.country(Country.builder().name(cityEntity.getCountryEntity().getName()).build())
			.name(cityEntity.getName()).build();
	}
}
