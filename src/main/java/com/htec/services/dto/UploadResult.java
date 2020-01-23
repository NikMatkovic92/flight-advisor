package com.htec.services.dto;

import java.io.Serializable;
import java.util.List;

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
public class UploadResult implements Serializable {

	private static final long serialVersionUID = -8800885264184191423L;
	List<String> failedLinesOnImport;
}
