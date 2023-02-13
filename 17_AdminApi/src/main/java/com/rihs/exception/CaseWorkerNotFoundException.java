package com.rihs.exception;

public class CaseWorkerNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CaseWorkerNotFoundException() {
		super();
	}
	
	public CaseWorkerNotFoundException(String msg) {
		super(msg);
	}
}
