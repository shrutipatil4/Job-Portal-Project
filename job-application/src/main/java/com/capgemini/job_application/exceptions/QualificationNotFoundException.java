package com.capgemini.job_application.exceptions;

public class QualificationNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public QualificationNotFoundException(String msg) {
		super(msg);
	}

}
