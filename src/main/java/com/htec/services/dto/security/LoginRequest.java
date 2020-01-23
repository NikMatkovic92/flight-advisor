package com.htec.services.dto.security;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

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
public class LoginRequest implements Serializable {

	private static final long serialVersionUID = 7320235257852400785L;
	@NotBlank
	private String username;

	@NotBlank
	private String password;

}
