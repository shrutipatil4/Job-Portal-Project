package com.capgemini.job_application;

import com.capgemini.job_application.controllers.SkillController;

import com.capgemini.job_application.entities.Skill;
import com.capgemini.job_application.exceptions.SkillNotFoundException;
import com.capgemini.job_application.services.SkillService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SkillController.class)
class SkillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SkillService skillService;

    private Skill skill;

    @BeforeEach
    void setUp() {
        skill = new Skill(1L, "Java");
    }

    @Test
    void testCreateSkill() throws Exception {
        when(skillService.saveSkill(any(Skill.class))).thenReturn(skill);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(skill)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skillName").value("Java"));
    }
    
    @Test
    void testCreateSkill_withInvalidSkillName_shouldReturnBadRequest() throws Exception {
        Skill invalidSkill = new Skill(1L, ""); 

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/skills")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(invalidSkill)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.skillName").value("Skill name must not be blank"));
    }

    @Test
    void testGetSkillById() throws Exception {
        when(skillService.getSkillById(1L)).thenReturn(skill);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/skills/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skillName").value("Java"));
    }
    
    @Test
    void testGetSkillById_NotFound() throws Exception {
        when(skillService.getSkillById(1L)).thenThrow(new SkillNotFoundException("Skill not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/skills/1"))
               .andExpect(status().isNotFound())
               .andExpect(jsonPath("$.message").value("Skill not found"));
    }

    

    @Test
    void testGetAllSkills() throws Exception {
        when(skillService.getAllSkills()).thenReturn(Arrays.asList(skill));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].skillName").value("Java"));
    }

    @Test
    void testUpdateSkill() throws Exception {
        Skill updatedSkill = new Skill(1L, "Advanced Java");
        when(skillService.updateSkill(eq(1L), any(Skill.class))).thenReturn(updatedSkill);

        mockMvc.perform(MockMvcRequestBuilders
                .put("/api/skills/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(updatedSkill)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.skillName").value("Advanced Java"));
    }

    @Test
    void testDeleteSkill() throws Exception {
        doNothing().when(skillService).deleteSkill(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/skills/1"))
                .andExpect(status().isNoContent());
    }
}

