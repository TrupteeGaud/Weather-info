package com.intigral.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.intigral.exceptions.CityNotFoundException;
import com.intigral.exceptions.CoordinatesNotFound;
import com.intigral.model.APIProperties;

@Service
public class WeatherService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	APIProperties apiProperties;

	public ResponseEntity<String> getWeatherbyCity(String city)
	{
		try {
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
			HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

			UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiProperties.getUrl())
					.queryParam("q", city)
					.queryParam("appid", apiProperties.getKey());

			ResponseEntity<String> responseEntity = restTemplate.exchange(
					uriBuilder.toUriString(),
					HttpMethod.GET,
					requestEntity,
					String.class
					);
			if(responseEntity.getStatusCode()== HttpStatus.OK)
				return responseEntity;
			else
				throw new CityNotFoundException(city);
		}catch (Exception e) {
			throw new CityNotFoundException(city);
		}

		
	}

	public ResponseEntity<String> getWeatherbyCoordinates(Double lat, Double lon)
	{
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<String> requestEntity = new HttpEntity<>(requestHeaders);

		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(apiProperties.getUrl())
				.queryParam("lat", lat)
				.queryParam("lon", lon)
				.queryParam("appid", apiProperties.getKey());

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				uriBuilder.toUriString(),
				HttpMethod.GET,
				requestEntity,
				String.class
				);

		if(responseEntity.getStatusCode()== HttpStatus.OK)
			return responseEntity;
		else
			throw new CoordinatesNotFound(lat,lon);
	}
}
