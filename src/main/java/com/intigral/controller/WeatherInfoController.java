package com.intigral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.intigral.exceptions.CoordinatesNotFound;
import com.intigral.service.WeatherService;

@RestController
@RequestMapping("/weatherservice")
public class WeatherInfoController {

	@Autowired
	WeatherService service;
	
	@GetMapping("/city")
	public ResponseEntity<String> getWeatherbyCity(@RequestParam("name") String city)
	{		
		return service.getWeatherbyCity(city);
	}
	
	@GetMapping("/coordinates")
	public ResponseEntity<String> getWeatherbycoordinates(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon)
	{		
		return service.getWeatherbyCoordinates(lat, lon);
	}
}
