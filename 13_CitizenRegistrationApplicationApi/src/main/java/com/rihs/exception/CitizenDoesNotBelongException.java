package com.rihs.exception;

public class CitizenDoesNotBelongException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CitizenDoesNotBelongException() {
		super();
	}

	public CitizenDoesNotBelongException(String errorMsg) {
		super(errorMsg);
	}

}
