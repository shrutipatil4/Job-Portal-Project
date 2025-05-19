package com.capgemini.job_application.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.job_application.dtos.CompanyDashBoardDto;
import com.capgemini.job_application.dtos.JobDto;
import com.capgemini.job_application.dtos.UserDashBoardDto;
import com.capgemini.job_application.entities.Application;
import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.exceptions.SkillNotFoundException;
import com.capgemini.job_application.exceptions.UserNotFoundException;
import com.capgemini.job_application.repositories.ApplicationRepository;
import com.capgemini.job_application.repositories.JobRepository;
import com.capgemini.job_application.repositories.SkillRepository;
import com.capgemini.job_application.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SkillRepository skillRepository, 
                           ApplicationRepository applicationRepository, JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public List<User> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        log.debug("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", id);
                    return new UserNotFoundException("User not found with ID: " + id);
                });
    }

    @Override
    public User createUser(User user) {
        log.debug("Creating new user with email: {}", user.getUserEmail());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        log.debug("Deleting user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        userRepository.delete(user);
        log.info("User deleted with ID: {}", id);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        existingUser.setUserName(user.getUserName());
        existingUser.setPhone(user.getPhone());
        existingUser.setPassword(user.getPassword());
        existingUser.setAddress(user.getAddress());
        existingUser.setUserType(user.getUserType());
        existingUser.setAge(user.getAge());
        existingUser.setGender(user.getGender());

        log.debug("Updating user with ID: {}", id);
        return userRepository.save(existingUser);
    }

    @Override
    public User findByUserEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return userRepository.findByUserEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public User findByUserNameOrUserEmail(String name, String email) {
        log.debug("Finding user by name: {} or email: {}", name, email);
        return userRepository.findByUserNameOrUserEmail(name, email)
                .orElseThrow(() -> new UserNotFoundException("User not found with name: " + name + " or email: " + email));
    }

    @Override
    public boolean existsByUserName(String name) {
        log.debug("Checking existence of username: {}", name);
        return userRepository.existsByUserName(name);
    }

    @Override
    public boolean existsByUserEmail(String email) {
        log.debug("Checking existence of user email: {}", email);
        return userRepository.existsByUserEmail(email);
    }

    @Override
    public User setUserSkill(Long userId, Long skillId) {
        log.debug("Setting skill with ID: {} for user with ID: {}", skillId, userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        user.getSkills().add(
                skillRepository.findById(skillId)
                        .orElseThrow(() -> new SkillNotFoundException("Skill not found with ID: " + skillId))
        );

        User updatedUser = userRepository.save(user);
        log.info("Added skill ID: {} to user ID: {}", skillId, userId);
        return updatedUser;
    }

    @Override
    public List<JobDto> getJobDto(Long userId) {
        log.debug("Fetching jobs available for user ID: {}", userId);
        return userRepository.findJobsToApply(userId);
    }

    @Override
    public UserDashBoardDto getUserDashBoardDto(Long userId) {
        log.debug("Building user dashboard for user ID: {}", userId);
        List<Application> applications = applicationRepository.findUserUserId(userId);

        long appliedCount = applications.size();
        long shortListed = applications.stream()
                .filter(app -> "SHORTLISTED".equalsIgnoreCase(app.getStatus()))
                .count();
        long rejected = applications.stream()
                .filter(app -> "REJECTED".equalsIgnoreCase(app.getStatus()))
                .count();
        long offered = applications.stream()
                .filter(app -> "OFFERED".equalsIgnoreCase(app.getStatus()))
                .count();

        List<JobDto> jobsToApply = getJobDto(userId);

        UserDashBoardDto userDto = new UserDashBoardDto();
        userDto.setAppliedCount(appliedCount);
        userDto.setShortListed(shortListed);
        userDto.setRejected(rejected);
        userDto.setOffered(offered);
        userDto.setJobsToApply(jobsToApply);

        return userDto;
    }

    @Override
    public CompanyDashBoardDto getDashboardForCompany(Long companyId) {
        log.debug("Building company dashboard for company ID: {}", companyId);

        long totalJobs = jobRepository.findByCompanyId(companyId).size();
        long totalApplications = applicationRepository.findByCompanyId(companyId).size();

        List<Object[]> genderCounts = applicationRepository.getGenderCounts(companyId);
        long male = 0L, female = 0L;
        for (Object[] row : genderCounts) {
            String gender = (String) row[0];
            Long count = (Long) row[1];
            if ("male".equalsIgnoreCase(gender)) male = count;
            else if ("female".equalsIgnoreCase(gender)) female = count;
        }

        List<Map<Long, Long>> jobApplicationStats = applicationRepository.findApplicationByJob(companyId).stream()
                .map(row -> Map.of((Long) row[0], (Long) row[1]))
                .collect(Collectors.toList());

        return new CompanyDashBoardDto(totalApplications, totalJobs, male, female, jobApplicationStats);
    }
}
