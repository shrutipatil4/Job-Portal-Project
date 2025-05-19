package com.capgemini.job_application.controllers;

import java.util.HashMap;


import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.job_application.dtos.LoginDto;
import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.exceptions.UserAlreadyExistsException;
import com.capgemini.job_application.security.JwtUtils;
import com.capgemini.job_application.services.UserService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*")



@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	AuthenticationManager authenticationManager;
	UserService userService;
	PasswordEncoder passwordEncoder;
	JwtUtils jwtService;
	
	@GetMapping
	public ResponseEntity<String> testAuth(){
		return ResponseEntity.status(200).body("api is working fine....");
	}



	@PostMapping("/signin")
	public ResponseEntity<Map<String, String>> authenticateUser(@RequestBody LoginDto loginDto) {
		
	    Authentication authentication = authenticationManager
	            .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getPassword()));
	    
	    
	    
	    if (authentication.isAuthenticated()) {
	    	
	        User user = userService.findByUserEmail(loginDto.getUserEmail());
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("email", user.getUserEmail());
	        claims.put("usertype", user.getUserType());
	        claims.put("userId", user.getUserId());
	        claims.put("sub", user.getUserName());
	        String token = jwtService.generateToken(loginDto.getUserEmail(), claims);

	        Map<String, String> response = new HashMap<>();
	        response.put("token", token);

	        return ResponseEntity.status(HttpStatus.OK).body(response);
	    }

	    Map<String, String> error = new HashMap<>();
	    error.put("error", "You are not Authorized !!");
	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}


	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		if (userService.existsByUserName(user.getUserName()) || userService.existsByUserEmail(user.getUserEmail()))
			throw new UserAlreadyExistsException("Username or Email Exists !");
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return ResponseEntity.status(HttpStatus.OK).body(userService.createUser(user));
	}
	
	

	
	
}
