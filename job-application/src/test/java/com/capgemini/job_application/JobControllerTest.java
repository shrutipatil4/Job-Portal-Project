package com.capgemini.job_application;

import com.capgemini.job_application.controllers.JobController;
import com.capgemini.job_application.entities.Company;
import com.capgemini.job_application.entities.Job;
import com.capgemini.job_application.services.JobService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobController.class)
class JobControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobService jobService;

    @Autowired
    private ObjectMapper objectMapper;
    
    private Job createSampleJob(Long id) {
    	Company company=new Company();
    	company.setCompanyId(100L);
        return new Job(
                id,
                company,
                "Java Developer",
                80000.0,
                "Develop Java applications",
                "Bangalore",
                LocalDate.now(),
                LocalDate.now().plusDays(10)
        );
    }

    @Test
    public void testGetAllJobs() throws Exception {
        List<Job> jobs = Arrays.asList(createSampleJob(1L), createSampleJob(2L));
        Mockito.when(jobService.getAllJobs()).thenReturn(jobs);

        mockMvc.perform(get("/api/jobs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetJobById() throws Exception {
        Job job = createSampleJob(1L);
        Mockito.when(jobService.getJobById(1L)).thenReturn(job);

        mockMvc.perform(get("/api/jobs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobId").value(1));
    }

    @Test
    public void testCreateJob() throws Exception {
        Job input = createSampleJob(null);
        Job saved = createSampleJob(1L);

        Mockito.when(jobService.createJob(any(Job.class))).thenReturn(saved);

        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/jobs/1"))
                .andExpect(jsonPath("$.jobId").value(1));
    }

    @Test
    public void testUpdateJob() throws Exception {
        Job updated = createSampleJob(1L);
        updated.setJobTitle("Senior Java Developer");

        Mockito.when(jobService.updateJob(Mockito.eq(1L), any(Job.class))).thenReturn(updated);

        mockMvc.perform(put("/api/jobs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobTitle").value("Senior Java Developer"));
    }


    @Test
    public void testDeleteJob() throws Exception {
        doNothing().when(jobService).deleteJob(1L);

        mockMvc.perform(delete("/api/jobs/1"))
                .andExpect(status().isNoContent());
    }

}
