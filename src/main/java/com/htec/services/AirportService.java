package com.htec.services;

import com.htec.services.dto.UploadResult;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Nikola Matkovic
 */
public interface AirportService {

	/**
	 * Inserts airport data into database based on file
	 *
	 * @param multipartFile - file for import
	 * @return lines that have failed on import
	 */
	UploadResult importAirportData(MultipartFile multipartFile);
}
