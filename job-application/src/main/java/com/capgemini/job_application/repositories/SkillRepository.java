package com.capgemini.job_application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.job_application.entities.Skill;

public interface SkillRepository extends JpaRepository<Skill,Long>{
	boolean existsBySkillName(String skillName);
}
