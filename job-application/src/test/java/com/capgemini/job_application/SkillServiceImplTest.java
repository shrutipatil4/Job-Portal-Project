package com.capgemini.job_application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.capgemini.job_application.entities.Skill;
import com.capgemini.job_application.exceptions.SkillNotFoundException;
import com.capgemini.job_application.repositories.SkillRepository;
import com.capgemini.job_application.services.SkillServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SkillServiceImplTest {

    @Mock
    private SkillRepository skillRepository;

    @InjectMocks
    private SkillServiceImpl skillService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveSkill() {
        Skill skill = new Skill(null, "Java");

        when(skillRepository.existsBySkillName("Java")).thenReturn(false);
        when(skillRepository.save(skill)).thenReturn(new Skill(1L, "Java"));

        Skill savedSkill = skillService.saveSkill(skill);

        assertNotNull(savedSkill);
        assertEquals("Java", savedSkill.getSkillName());
    }

    @Test
    void testGetSkillById() {
        Skill skill = new Skill(1L, "Spring");

        when(skillRepository.findById(1L)).thenReturn(Optional.of(skill));

        Skill result = skillService.getSkillById(1L);

        assertNotNull(result);
        assertEquals("Spring", result.getSkillName());
    }

    @Test
    void testGetAllSkills() {
        List<Skill> skills = Arrays.asList(new Skill(1L, "Java"), new Skill(2L, "Python"));

        when(skillRepository.findAll()).thenReturn(skills);

        List<Skill> result = skillService.getAllSkills();

        assertEquals(2, result.size());
    }

    @Test
    void testUpdateSkill() {
        Skill existingSkill = new Skill(1L, "Java");
        Skill newSkill = new Skill(null, "Advanced Java");

        when(skillRepository.findById(1L)).thenReturn(Optional.of(existingSkill));
        when(skillRepository.save(any(Skill.class))).thenReturn(new Skill(1L, "Advanced Java"));

        Skill result = skillService.updateSkill(1L, newSkill);

        assertEquals("Advanced Java", result.getSkillName());
    }
    
    @Test
    void testUpdateSkill_SkillNotFoundException() {
        Long skillId = 99L;
        Skill newSkill = new Skill(null, "Unknown");

        when(skillRepository.findById(skillId)).thenReturn(Optional.empty());

        assertThrows(SkillNotFoundException.class, () -> {
            skillService.updateSkill(skillId, newSkill);
        });
    }

    @Test
    void testDeleteSkill() {
        skillService.deleteSkill(1L);
        verify(skillRepository, times(1)).deleteById(1L);
    }
}
