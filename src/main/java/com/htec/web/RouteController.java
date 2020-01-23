package com.htec.web;

import com.htec.services.RouteService;
import com.htec.services.dto.FlightRoute;
import com.htec.services.dto.UploadResult;

import java.util.List;

import javax.ws.rs.BadRequestException;

import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/routes")
@AllArgsConstructor
public class RouteController {

	private final RouteService routeService;

	@PostMapping("/uploadFile")
	@PreAuthorize("hasRole('ADMIN')")
	public UploadResult uploadFile(@RequestParam("file") MultipartFile file) {

		if(!"text/plain".equals(file.getContentType())) {
			throw new BadRequestException("File should be in .txt format");
		}
		return routeService.uploadRoute(file);
	}

	@PostMapping
	@PreAuthorize("hasRole('USER')")
	public List<FlightRoute> getRoute(@RequestParam String from, @RequestParam String to) {

		return routeService.getRoute(from, to);
	}
}
