package com.htec.web;

import com.htec.services.AirportService;
import com.htec.services.dto.UploadResult;

import javax.ws.rs.BadRequestException;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

/**
 * @author Nikola Matkovic
 */
@RestController
@RequestMapping("/api/airports")
@AllArgsConstructor
public class AirportController {

	private final AirportService airportService;

	@PostMapping("/uploadFile")
	@PreAuthorize("hasRole('ADMIN')")
	public UploadResult uploadFile(@RequestParam("file") MultipartFile file) {

		if(!"text/plain".equals(file.getContentType())) {
			throw new BadRequestException("File should be in .txt format");
		}
		return airportService.importAirportData(file);
	}
}
