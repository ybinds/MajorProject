package com.rihs.exception;

public class PlanNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlanNotFoundException() {
		super();
	}
	
	public PlanNotFoundException(String msg) {
		super(msg);
	}
}
