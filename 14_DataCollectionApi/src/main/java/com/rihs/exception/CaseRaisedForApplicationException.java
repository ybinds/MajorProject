package com.rihs.exception;

public class CaseRaisedForApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CaseRaisedForApplicationException() {
		super();
	}

	public CaseRaisedForApplicationException(String msg) {
		super(msg);
	}
}
