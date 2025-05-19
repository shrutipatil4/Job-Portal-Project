package com.capgemini.job_application.controllers;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;

import com.capgemini.job_application.dtos.UserDashBoardDto;
import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/users")
@Slf4j 
//@PreAuthorize("hasRole('USER') or hasRole('COMPANY')")
public class UserController {
	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
		log.info("Received request to fetch all users");
        List<User> users = userService.getAllUsers();
        log.debug("Returning {} users", users.size()); 
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<User> getUser( @PathVariable Long id) {
		log.info("Received request to fetch user with ID: {}", id); 
        User user = userService.getUserById(id);
        log.debug("User fetched: {}", user); 
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
	
	@PostMapping
	public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult result) {
	    if (result.hasErrors()) {
	        log.warn("Validation failed for user creation: {}", result.getAllErrors());
	        throw new IllegalArgumentException(result.getFieldErrors().toString());
	    }
	    log.info("Received request to create user: {}", user);
	    User savedUser = userService.createUser(user);
	    log.debug("User created with ID: {}", savedUser.getUserId());
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}

	
	@PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user,  BindingResult result) {
		if(result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}
		log.info("Received request to update user: {}", user); 
		User updatedUser = userService.updateUser(id, user);
		log.debug("User updated with ID: {}", updatedUser.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		log.info("Received request to delete user with ID: {}", id); 
        userService.deleteUser(id);
        log.info("User with ID {} successfully deleted", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
	
	@PostMapping("/{userId}/addSkill")
	public ResponseEntity<User> setUserSkill(@PathVariable Long userId, @RequestBody Map<String, Long> body) {
	    Long skillId = body.get("skillId");
	    return ResponseEntity.ok(userService.setUserSkill(userId, skillId));
	}
	
	@GetMapping("/userDto/{userId}")
	public ResponseEntity<UserDashBoardDto> getUserDto(@PathVariable Long userId){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserDashBoardDto(userId));
	}
	
}
	

