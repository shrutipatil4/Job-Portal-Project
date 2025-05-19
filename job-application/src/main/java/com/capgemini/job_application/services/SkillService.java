package com.capgemini.job_application.services;

import java.util.List;

import com.capgemini.job_application.entities.Skill;

public interface SkillService {
	Skill saveSkill(Skill skill);

	Skill getSkillById(Long skillId);

	List<Skill> getAllSkills();

	Skill updateSkill(Long skillId, Skill skill);

	void deleteSkill(Long skillId);
}
