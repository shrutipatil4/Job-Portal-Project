package com.capgemini.job_application.entities;

import java.util.*;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name="skill")
public class Skill {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="skill_id")
	private Long skillId;
	
	@NotBlank(message = "Skill name must not be blank")
	@Column(name ="skill_name")
	private String skillName;
	

	@ManyToMany(mappedBy = "skills")
	private List<User> users = new ArrayList<>();
		
	public Skill() {

	}

	public Skill(Long skillId, String skillName) {
		super();
		this.skillId = skillId;
		this.skillName = skillName;
	}
	

	public Long getSkillId() {
		return skillId;
	}

	public void setSkillId(Long skillId) {
		this.skillId = skillId;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	@Override
	public String toString() {
		return "Skill [skillId=" + skillId + ", skillName=" + skillName +  "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(skillId, skillName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Skill other = (Skill) obj;
		return Objects.equals(skillId, other.skillId) && Objects.equals(skillName, other.skillName);
	}

    
	
	
}
