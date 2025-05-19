package com.capgemini.job_application.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.capgemini.job_application.dtos.CompanyDashBoardDto;
import com.capgemini.job_application.dtos.JobDto;
import com.capgemini.job_application.dtos.UserDashBoardDto;
import com.capgemini.job_application.entities.User;

@Service
public interface UserService {
	
	List<User> getAllUsers ();
	
	User getUserById(Long id);
	
	User createUser(User user);
	
	void deleteUser(Long id);
	
	User updateUser(Long id, User user);
	
	User findByUserEmail(String email);

	User findByUserNameOrUserEmail(String name, String email);
	
	boolean existsByUserName(String name);

	boolean existsByUserEmail(String email);
	
	User setUserSkill(Long userId , Long skillId);
	
	List<JobDto> getJobDto(Long userId);
	
	UserDashBoardDto getUserDashBoardDto(Long userId);
	
	public CompanyDashBoardDto getDashboardForCompany(Long companyId);
}
