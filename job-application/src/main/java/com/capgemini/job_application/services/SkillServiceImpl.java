package com.capgemini.job_application.services;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.capgemini.job_application.entities.Skill;
import com.capgemini.job_application.exceptions.SkillNotFoundException;
import com.capgemini.job_application.repositories.SkillRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SkillServiceImpl implements SkillService {
	private SkillRepository skillRepository;

	@Autowired
	public SkillServiceImpl(SkillRepository skillRepository) {

		this.skillRepository = skillRepository;
	}

	@Override
	public Skill saveSkill(Skill skill) {
		log.info("Saving new skill: {}", skill.getSkillName());
		log.debug("Skill object received: {}", skill);
		
		if (skillRepository.existsBySkillName(skill.getSkillName())) {
			log.error("Skill already exists: {}", skill.getSkillName());
	        throw new IllegalArgumentException("Skill with this name already exists.");
	    }
		log.info("skill saved");
	    return skillRepository.save(skill);
	}

	@Override
	public Skill getSkillById(Long skillId) {
		Optional<Skill> optionalSkill = skillRepository.findById(skillId);
		if (optionalSkill.isPresent()) {
			log.info("Skill found: {}", optionalSkill.get().getSkillName());
		} else {
			log.warn("Skill not found with ID: {}", skillId);
		}
		return optionalSkill.orElse(null);
		
	}

	@Override
	public List<Skill> getAllSkills() {
		log.info("get all skills");
		return skillRepository.findAll();
	}

	@Override
	public Skill updateSkill(Long skillId, Skill skill) {

		Skill skills = skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException("Skill with ID " + skillId + " not found"));
		skills.setSkillName(skill.getSkillName());
		log.info("skill updated");
		return skillRepository.save(skills);
	}

	@Override
	public void deleteSkill(Long skillId) {
		skillRepository.deleteById(skillId);
		log.info("Skill deleted with ID: {}", skillId);
	}

}
