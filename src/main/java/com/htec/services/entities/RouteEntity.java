package com.htec.services.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Nikola Matkovic
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Table
public class RouteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@ManyToOne
	private AirlineEntity airlineEntity;

	@ManyToOne
	private AirportEntity sourceAirport;

	@ManyToOne
	private AirportEntity destinationAirport;

	@Column(length = 1)
	private String codeshare;

	@Column
	private Integer stops;

	@Column
	private String equipment;

	@Column
	private BigDecimal price;

}
