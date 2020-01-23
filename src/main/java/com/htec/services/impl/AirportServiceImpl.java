package com.htec.services.impl;

import com.htec.services.AirportService;
import com.htec.services.dto.UploadResult;
import com.htec.services.entities.AirportEntity;
import com.htec.services.entities.CityEntity;
import com.htec.services.entities.CountryEntity;
import com.htec.services.entities.DaylightSavingsTime;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.ws.rs.BadRequestException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Nikola Matkovic
 */

@Service
@AllArgsConstructor
@Slf4j
@Transactional
class AirportServiceImpl implements AirportService {

	private AirportRepository airportRepository;
	private CountryRepository countryRepository;
	private CityRepository cityRepository;

	@Override
	public UploadResult importAirportData(final MultipartFile multipartFile) {
		List<String> failedLines = new ArrayList<>();;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
			reader.lines().forEach(line -> {
				var records = line.replaceAll("\"", "").split(",");
				var city = cityRepository.findByName(records[2]);
				var country = countryRepository.findByName(records[3]);
				try {
					if (!city.isEmpty() && country != null) {
						city.stream().filter(cityEntity -> cityEntity.getCountryEntity().getName().equals(country.getName())).forEach(cityEntity ->
							saveAirport(records, country, cityEntity));
					}
				} catch (Exception e) {
					failedLines.add(line);
					log.error("Error encountered while processing file, skipping record", e);
				}
			});
			reader.close();
			return UploadResult.builder().failedLinesOnImport(failedLines).build();
		} catch (Exception e) {
			log.error("Error encountered while processing file", e);
			throw new BadRequestException("Error encountered while processing file with exception: " + e.getMessage());
		}
	}

	private void saveAirport(final String[] records, final CountryEntity country, final CityEntity cityEntity) {
		var airport = AirportEntity.builder().countryEntity(country)
			.cityEntity(cityEntity)
			.name(records[1])
			.iataAirportCode(records[4])
			.icaoAirportcode(records[5])
			.latitude(new BigDecimal(records[6]).setScale(6, RoundingMode.HALF_UP))
			.longitude(new BigDecimal(records[7]).setScale(6, RoundingMode.HALF_UP))
			.altitude(Integer.valueOf(records[8]))
			.timezone(Integer.valueOf(records[9]))
			.dst(DaylightSavingsTime.valueOf(records[10]))
			.tz(ZoneId.of(records[11]))
			.type(records[12])
			.source(records[13])
			.build();

		airportRepository.save(airport);
	}
}
