package com.capgemini.job_application.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserAlreadyExistsException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(ApplicationNotFoundException.class)
	public ResponseEntity<String> handleApplicationNotFoundException(ApplicationNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	

	@ExceptionHandler(CompanyNotFoundException.class)
	public ResponseEntity<String> handleCompanyNotFoundException(CompanyNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(ExperienceNotFoundException.class)
	public ResponseEntity<String> handleExperienceNotFoundException(ExperienceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(JobNotFoundException.class)
	public ResponseEntity<String> handleJobNotFoundException(JobNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(QualificationNotFoundException.class)
	public ResponseEntity<String> handleQualificationNotFoundException(QualificationNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(SkillNotFoundException.class)
	public ResponseEntity<String> handleSkillNotFoundException(SkillNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(IllegalArgumentException.class) 
    public ResponseEntity<String> handleAll(IllegalArgumentException ex) { 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
	
	@ExceptionHandler(Exception.class) 
    public ResponseEntity<String> handleAll(Exception ex) { 
        return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR); 
    } 
	
	
	
}
