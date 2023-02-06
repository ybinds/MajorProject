package com.rihs.exception;

public class CitizenApplicationNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CitizenApplicationNotFoundException() {
		super();
	}
	
	public CitizenApplicationNotFoundException(String msg) {
		super(msg);
	}
}
