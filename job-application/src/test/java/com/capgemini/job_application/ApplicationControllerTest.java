package com.capgemini.job_application;

import com.capgemini.job_application.controllers.ApplicationController;

import com.capgemini.job_application.entities.Application;
import com.capgemini.job_application.entities.Job;
import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.services.ApplicationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Application application;
    

    @BeforeEach
    void setUp() {
    	User user=new User();
    	user.setUserId(101L);
    	Job job=new Job();
    	job.setJobId(101L);
        application = new Application();
        application.setApplicationId(1L);
        application.setJob(job);
        application.setUser(user);
        application.setAppliedDate(LocalDate.now());
        application.setStatus("Pending");
    }

    @Test
    void testCreateApplication() throws Exception {
        when(applicationService.createApplication(any(Application.class))).thenReturn(application);

        mockMvc.perform(post("/api/application")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(application)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.applicationId").value(1L))
                .andExpect(jsonPath("$.status").value("Pending"));
    }

    @Test
    void testGetAllApplications() throws Exception {
        List<Application> list = Arrays.asList(application);
        when(applicationService.getAllApplication()).thenReturn(list);

        mockMvc.perform(get("/api/application"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].jobId").value(101L));
    }

    @Test
    void testGetApplicationById() throws Exception {
        when(applicationService.getApplicationById(1L)).thenReturn(application);

        mockMvc.perform(get("/api/application/1"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.applicationId").value(1L))
                .andExpect(jsonPath("$.status").value("Pending"));
    }

    @Test
    void testUpdateApplication() throws Exception {
    	User user=new User();
    	user.setUserId(501L);
    	Job job=new Job();
    	job.setJobId(102L);
        Application updated = new Application();
        updated.setApplicationId(1L);
        updated.setJob(job);
        updated.setUser(user);
        updated.setAppliedDate(LocalDate.now());
        updated.setStatus("Approved");

        when(applicationService.updateApplication(eq(1L), any(Application.class))).thenReturn(updated);

        mockMvc.perform(put("/api/application/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Approved"))
                .andExpect(jsonPath("$.jobId").value(102L));
    }

    @Test
    void testDeleteApplication() throws Exception {
        doNothing().when(applicationService).deleteApplication(1L);

        mockMvc.perform(delete("/api/application/1"))
                .andExpect(status().isOk());
    }
}