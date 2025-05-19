package com.capgemini.job_application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.job_application.entities.Skill;
import com.capgemini.job_application.services.SkillService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private SkillService skillService;

    @Autowired
	public SkillController(SkillService skillService) {
		this.skillService = skillService;
	}
    
    @PostMapping
    public Skill createSkill(@Valid @RequestBody Skill skill, BindingResult result) {
    	log.trace("Entered Create Skill");
   			
    	if(result.hasErrors()) {
    		log.error("Error found in create skill. invalid data");

    		throw new IllegalArgumentException(result.getAllErrors().toString());
    	}
    	log.info("New Skill Created");
   		
        return skillService.saveSkill(skill);
    }
    
    @GetMapping("/{skillId}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Long skillId) {
        Skill skill = skillService.getSkillById(skillId);
        if (skill == null) {
        	log.warn("Skill with ID {} not found ", skillId);
            return ResponseEntity.notFound().build();
        }
        log.info("Skill with ID {} retrieved successfully", skillId);
        return ResponseEntity.ok(skill);
    }
    
    @GetMapping
    public List<Skill> getAllSkills() {
    	log.info("skills fetched");
        return skillService.getAllSkills();
    }
    
    @PutMapping("/{skillId}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Long skillId,@Valid @RequestBody Skill skill,BindingResult result) {
    	if(result.hasErrors()) {
    		log.error("Error found in updating skill. invalid data");
    		throw new IllegalArgumentException(result.getFieldErrors().toString());
    	}
    	Skill updatedSkill = skillService.updateSkill(skillId, skill);
        if (updatedSkill == null) {
            return ResponseEntity.notFound().build();
        }
        log.info("skillname updated");
        return ResponseEntity.ok(updatedSkill);
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long skillId) {
        skillService.deleteSkill(skillId);
        log.info("Skill with id {}, deleted",skillId);
        return ResponseEntity.noContent().build();
    }
    
}
