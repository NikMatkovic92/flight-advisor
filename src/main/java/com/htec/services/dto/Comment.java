package com.htec.services.dto;

import java.io.Serializable;

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
public class Comment implements Serializable {

	private static final long serialVersionUID = -6631616685191849059L;
	private Long id;
	private Long cityId;

	@NotNull
	private String text;
}
