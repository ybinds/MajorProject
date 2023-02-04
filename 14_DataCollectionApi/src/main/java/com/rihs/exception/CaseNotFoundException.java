package com.rihs.exception;

public class CaseNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CaseNotFoundException(){
		super();
	}
	
	public CaseNotFoundException(String msg) {
		super(msg);
	}
}
