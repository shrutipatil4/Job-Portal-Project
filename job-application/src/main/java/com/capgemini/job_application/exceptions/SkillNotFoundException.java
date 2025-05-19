package com.capgemini.job_application.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SkillNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public SkillNotFoundException(String message) {
		super(message);
	}

}
