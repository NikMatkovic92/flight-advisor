package com.htec.services.entities;

import java.math.BigDecimal;
import java.time.ZoneId;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class AirportEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column
	private String name;

	@ManyToOne
	private CityEntity cityEntity;

	@ManyToOne
	private CountryEntity countryEntity;

	@Column(length = 3)
	private String iataAirportCode;

	@Column(length = 4)
	private String icaoAirportcode;

	@Column(scale = 6, precision = 10)
	private BigDecimal latitude;

	@Column(scale = 6, precision = 10)
	private BigDecimal longitude;

	@Column
	private Integer altitude;

	@Column
	private Integer timezone;

	@Enumerated(EnumType.STRING)
	private DaylightSavingsTime dst;

	@Column
	private ZoneId tz;

	@Column
	private String type;

	@Column
	private String source;
}
