package com.htec.services.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.NotNull;

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
public class Country implements Serializable {

	private static final long serialVersionUID = 666566041703608454L;
	@NotNull
	private String name;

	private Set<City> cities;
}
