package com.htec.services.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlightRoute implements Serializable {

	private static final long serialVersionUID = 1184148536105041924L;
	private String sourceCity;

	private String destinationCity;

	private String airline;

	private BigDecimal price;

	private Integer durationInMinutes;
}
