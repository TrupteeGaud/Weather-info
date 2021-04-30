package com.intigral.exceptions;

public class CityNotFoundException extends RuntimeException {

	public CityNotFoundException(String city) {

        super(String.format("City with name %s not found", city));
    }
}
