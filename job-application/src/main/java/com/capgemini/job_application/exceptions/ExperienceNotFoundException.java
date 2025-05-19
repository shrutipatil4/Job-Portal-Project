package com.capgemini.job_application.exceptions;

public class ExperienceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public ExperienceNotFoundException(String msg) {
		super(msg);
	}

}
