package com.intigral.exceptions;

public class CoordinatesNotFound extends RuntimeException {

	public CoordinatesNotFound(Double lat, Double lon) {
		super(String.format("Coordinates with latitude %d and longitude %d not found", lat,lon));
	}

	
}
