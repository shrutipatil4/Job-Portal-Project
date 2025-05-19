package com.capgemini.job_application;

import com.capgemini.job_application.controllers.ExperienceController;

import com.capgemini.job_application.entities.Experience;
import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.services.ExperienceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(ExperienceController.class)
class ExperienceControllerTest {
    private MockMvc mockMvc;
    private ExperienceService experienceService;
    private ObjectMapper objectMapper;
    
    @Autowired
    public ExperienceControllerTest(MockMvc mockMvc, ExperienceService experienceService, ObjectMapper objectMapper) {
		super();
		this.mockMvc = mockMvc;
		this.experienceService = experienceService;
		this.objectMapper = objectMapper;
	}

	@Test
    void testGetExperienceByUserId() throws Exception {
		User user = new User();
        user.setUserId(100L);
        Experience exp = new Experience();
        exp.setExperienceId(1L);
        exp.setUser(user);
        exp.setRole("Developer");
        exp.setCompanyName("TechCorp");
        exp.setStartDate(LocalDate.of(2020, 1, 1));
        exp.setEndDate(LocalDate.of(2022, 1, 1));

        List<Experience> experiences = Arrays.asList(exp);
        Mockito.when(experienceService.getExperienceByUserId(100L)).thenReturn(experiences);

        mockMvc.perform(get("/api/experiences/100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].role").value("Developer"));
    }

    @Test
    void testCreateExperience() throws Exception {
    	User user = new User();
        user.setUserId(101L);
        Experience input = new Experience();
        input.setUser(user);
        input.setRole("Tester");
        input.setCompanyName("Test Inc");
        input.setStartDate(LocalDate.of(2021, 1, 1));
        input.setEndDate(LocalDate.of(2023, 1, 1));

        Experience saved = new Experience();
        saved.setExperienceId(1L);
        saved.setUser(user);
        saved.setRole("Tester");
        saved.setCompanyName("Test Inc");
        saved.setStartDate(LocalDate.of(2021, 1, 1));
        saved.setEndDate(LocalDate.of(2023, 1, 1));

        Mockito.when(experienceService.createExperience(any(Experience.class))).thenReturn(saved);

        mockMvc.perform(post("/api/experiences")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.experienceId").value(1L));
    }

    @Test
    void testDeleteExperienceById() throws Exception {
        Mockito.doNothing().when(experienceService).deleteExperienceById(1L);

        mockMvc.perform(delete("/api/experiences/delete/experience/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteAllExperiences() throws Exception {
        Mockito.doNothing().when(experienceService).deleteAllExperiencesByUserId(101L);

        mockMvc.perform(delete("/api/experiences/delete/101"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateExperience() throws Exception {
    	User user = new User();
        user.setUserId(100L);
        Experience updatedInput = new Experience();
        updatedInput.setUser(user);
        updatedInput.setRole("Senior Developer");
        updatedInput.setCompanyName("NewTech");
        updatedInput.setStartDate(LocalDate.of(2020, 1, 1));
        updatedInput.setEndDate(LocalDate.of(2024, 1, 1));

        Experience updatedOutput = new Experience();
        updatedOutput.setExperienceId(1L);
        updatedOutput.setUser(user);
        updatedOutput.setRole("Senior Developer");
        updatedOutput.setCompanyName("NewTech");
        updatedOutput.setStartDate(LocalDate.of(2020, 1, 1));
        updatedOutput.setEndDate(LocalDate.of(2024, 1, 1));

        Mockito.when(experienceService.updateExperience(eq(1L), eq(100L), any(Experience.class)))
                .thenReturn(updatedOutput);

        mockMvc.perform(put("/api/experiences/1/update/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.role").value("Senior Developer"))
                .andExpect(jsonPath("$.companyName").value("NewTech"));
    }
}

