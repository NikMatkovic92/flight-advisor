package com.htec.services.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.htec.services.entities.AirlineEntity;
import com.htec.services.entities.AirportEntity;
import com.htec.services.entities.RouteEntity;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLDataException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

/**
 * @author Nikola Matkovic
 */
@SpringBootTest
class RouteServiceImplTest {

	@Mock
	private AirportRepository airportRepository;

	@Mock
	private AirlineRepository airlineRepository;

	@Mock
	private RouteRepository routeRepository;

	@InjectMocks
	private RouteServiceImpl routeService;

	@Test
	void uploadRoute() throws IOException {

		ClassLoader classLoader = getClass().getClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream("files/testRoutes.txt");
		MockMultipartFile multipartFile = new MockMultipartFile("file", inputStream);

		var mockAirline = mock(AirlineEntity.class);
		var mockAirport = mock(AirportEntity.class);

		when(airlineRepository.findByIataAirlineCodeOrIcaoAirlineCode(anyString())).thenReturn(mockAirline);
		when(airportRepository.findByIataAirportCodeOrIcaoAirportCode(anyString())).thenReturn(mockAirport);

		routeService.uploadRoute(multipartFile);

		verify(routeRepository, times(2)).save(any());
	}

	@Test
	void getRoute() {
		var route1 = setupRoute(-6.081689834590001, 145.391998291, -5.20707988739, 145.789001465, 50.20);
		var route2 = setupRoute(5.826789855957031, 144.29600524902344, 67.0122218992, -50.7116031647, 150.20);
		List<RouteEntity> routes = Arrays.asList(route1, route2);

		when(routeRepository.findRouteForCities(anyString(), anyString())).thenReturn(routes);

		var result = routeService.getRoute("Goroka", "Nadzab");

		Assert.assertEquals(2, result.size());
		Assert.assertEquals(0, result.get(0).getPrice().compareTo(BigDecimal.valueOf(50.20)));

	}

	private RouteEntity setupRoute(double sLat, double sLon, double dLat, double dLon, double price) {
		var sourceAirport = AirportEntity.builder().latitude(BigDecimal.valueOf(sLat)).longitude(BigDecimal.valueOf(sLon)).build();
		var destinationAirport = AirportEntity.builder().latitude(BigDecimal.valueOf(dLat)).longitude(BigDecimal.valueOf(dLon)).build();
		var airline = AirlineEntity.builder().iataAirlineCode("CC7").build();
		return RouteEntity.builder().airlineEntity(airline).sourceAirport(sourceAirport).destinationAirport(destinationAirport).price(BigDecimal.valueOf(price))
			.build();
	}
}