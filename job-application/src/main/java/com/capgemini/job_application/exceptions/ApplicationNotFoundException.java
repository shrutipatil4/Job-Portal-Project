package com.capgemini.job_application.exceptions;

public class ApplicationNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ApplicationNotFoundException(String msg) {
		super(msg);
	}

}
