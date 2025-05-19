package com.capgemini.job_application.services;

import com.capgemini.job_application.entities.Qualification;
import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.exceptions.QualificationNotFoundException;
import com.capgemini.job_application.exceptions.UserNotFoundException;
import com.capgemini.job_application.repositories.QualificationRepository;
import com.capgemini.job_application.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class QualificationServiceImpl implements QualificationService {

    private final QualificationRepository qualificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public QualificationServiceImpl(QualificationRepository qualificationRepository, UserRepository userRepository) {
        this.qualificationRepository = qualificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Qualification> getAllQualifications() {
        log.info("Fetching all qualifications");
        return qualificationRepository.findAll();
    }

    @Override
    public Qualification findQualificationById(Long id) {
        return qualificationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Qualification not found with ID: {}", id);
                    return new QualificationNotFoundException("Qualification not found with ID: " + id);
                });
    }

    @Override
    public Qualification createQualification(Qualification qualification) {
        Long userId = qualification.getUser().getUserId();
        log.info("Creating qualification for user ID: {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.warn("User not found with ID: {}", userId);
                    return new UserNotFoundException("User with ID " + userId + " not found");
                });

        qualification.setUser(user);
        Qualification saved = qualificationRepository.save(qualification);
        log.info("Qualification created successfully with ID: {}", saved.getQualificationId());
        return saved;
    }

    @Override
    public Qualification updateQualification(Long id, Qualification qualification) {
        Qualification existing = findQualificationById(id);

        existing.setDegree(qualification.getDegree());
        existing.setInstitute(qualification.getInstitute());
        existing.setQualificationType(qualification.getQualificationType());
        existing.setStartDate(qualification.getStartDate());
        existing.setEndDate(qualification.getEndDate());
        existing.setUrl(qualification.getUrl());

        Qualification updated = qualificationRepository.save(existing);
        log.info("Qualification updated with ID: {}", updated.getQualificationId());
        return updated;
    }

    @Override
    public Qualification patchQualification(Long id, Qualification qualification) {
        Qualification existing = findQualificationById(id);

        if (qualification.getDegree() != null) {
            existing.setDegree(qualification.getDegree());
        }
        if (qualification.getInstitute() != null) {
            existing.setInstitute(qualification.getInstitute());
        }
        if (qualification.getQualificationType() != null) {
            existing.setQualificationType(qualification.getQualificationType());
        }
        if (qualification.getStartDate() != null) {
            existing.setStartDate(qualification.getStartDate());
        }
        if (qualification.getEndDate() != null) {
            existing.setEndDate(qualification.getEndDate());
        }
        if (qualification.getUrl() != null) {
            existing.setUrl(qualification.getUrl());
        }

        Qualification patched = qualificationRepository.save(existing);
        log.info("Qualification patched successfully with ID: {}", patched.getQualificationId());
        return patched;
    }

    @Override
    public void deleteQualification(Long id) {
        if (!qualificationRepository.existsById(id)) {
            log.warn("Attempted to delete non-existent qualification with ID: {}", id);
            throw new QualificationNotFoundException("Qualification not found with ID: " + id);
        }
        qualificationRepository.deleteById(id);
        log.info("Deleted qualification with ID: {}", id);
    }

    @Override
    public List<Qualification> findByUserId(Long userId) {
        log.info("Fetching qualifications for user ID: {}", userId);
        return qualificationRepository.findByUserId(userId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        log.info("Deleting qualifications for user ID: {}", userId);
        qualificationRepository.deleteByUserId(userId);
    }
}
