package com.htec.services.impl;

import static java.math.BigDecimal.valueOf;

import com.htec.services.RouteService;
import com.htec.services.dto.FlightRoute;
import com.htec.services.dto.UploadResult;
import com.htec.services.entities.AirlineEntity;
import com.htec.services.entities.AirportEntity;
import com.htec.services.entities.RouteEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
@AllArgsConstructor
@Service
@Slf4j
@Transactional
class RouteServiceImpl implements RouteService {

	private static final BigDecimal AVERAGE_PLANE_MPH = valueOf(600);
	private final AirportRepository airportRepository;
	private final AirlineRepository airlineRepository;
	private final RouteRepository routeRepository;

	@Override
	public UploadResult uploadRoute(final MultipartFile file) {
		List<String> failedLines = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
			reader.lines().forEach(line -> {
				var records = line.split(",");
				var airline = airlineRepository.findByIataAirlineCodeOrIcaoAirlineCode(records[0]);
				var sourceAirport = airportRepository.findByIataAirportCodeOrIcaoAirportCode(records[2]);
				var destinationAirport = airportRepository.findByIataAirportCodeOrIcaoAirportCode(records[4]);

				try {
					if (airline != null && sourceAirport != null && destinationAirport != null) {
						saveRoute(records, airline, sourceAirport, destinationAirport);
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

	@Override
	public List<FlightRoute> getRoute(final String sourceCity, final String destinationCity) {

		List<FlightRoute> routes = new ArrayList<>();

		List<RouteEntity> routeForCities = routeRepository.findRouteForCities(sourceCity, destinationCity);
		if (routeForCities.isEmpty()) {
			return Collections.emptyList();
		}

		routeForCities.forEach(routeEntity -> {

			var sLat = routeEntity.getSourceAirport().getLatitude().doubleValue();
			var sLon = routeEntity.getSourceAirport().getLongitude().doubleValue();
			var dLat = routeEntity.getDestinationAirport().getLatitude().doubleValue();
			var dLon = routeEntity.getDestinationAirport().getLongitude().doubleValue();

			BigDecimal distance = valueOf(getDistance(sLat, sLon, dLat, dLon));

			BigDecimal duration = BigDecimal.ZERO.compareTo(distance) != 0 ? distance.divide(AVERAGE_PLANE_MPH, RoundingMode.HALF_UP) : BigDecimal.ZERO;

			routes.add(
				FlightRoute.builder().airline(routeEntity.getAirlineEntity().getIataAirlineCode() != null ?
					routeEntity.getAirlineEntity().getIataAirlineCode() :
					routeEntity.getAirlineEntity().getIcaoAirlineCode()).sourceCity(sourceCity).destinationCity(destinationCity)
					.price(routeEntity.getPrice()).durationInMinutes(duration.multiply(valueOf(60)).intValue()).build());
		});
		routes.sort(Comparator.comparing(FlightRoute::getPrice));
		return routes;
	}

	private void saveRoute(final String[] records, final AirlineEntity airline, final AirportEntity sourceAirport, final AirportEntity destinationAirport) {
		var route = RouteEntity.builder().airlineEntity(airline)
			.sourceAirport(sourceAirport)
			.destinationAirport(destinationAirport)
			.codeshare(records[6])
			.stops(Integer.valueOf(records[7]))
			.equipment(records[8])
			.price(new BigDecimal(records[9]))
			.build();

		routeRepository.save(route);
	}

	private double getDistance(Double lat1, Double lon1, Double lat2, Double lon2) {
		if ((lat1.equals(lat2)) && (lon1.equals(lon2))) {
			return 0;
		} else {
			var theta = lon1 - lon2;
			var dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			return (dist);
		}
	}
}
