package com.htec.services.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class AirlineEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;

	@Column(length = 2)
	private String iataAirlineCode;

	@Column(length = 3)
	private String icaoAirlineCode;

}
