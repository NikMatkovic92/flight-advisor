package com.htec.services.dto;

import java.io.Serializable;
import java.util.Set;

import org.springframework.lang.NonNull;

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
public class City implements Serializable {

	private static final long serialVersionUID = -9102725621945714602L;
	@NonNull
	private String name;

	@NonNull
	private String description;

	private Set<Comment> comments;

	@NonNull
	private Country country;
}
