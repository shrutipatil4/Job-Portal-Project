package com.capgemini.job_application;

import com.capgemini.job_application.controllers.QualificationController;
import com.capgemini.job_application.entities.Qualification;
import com.capgemini.job_application.entities.User;
import com.capgemini.job_application.services.QualificationService;
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


@WebMvcTest(QualificationController.class)
class QualificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QualificationService qualificationService;

    @Autowired
    private ObjectMapper objectMapper;

    private Qualification qualification;

    @BeforeEach
    void setUp() {
    	User user=new User();
    	user.setUserId(100L);
        qualification = new Qualification();
        qualification.setQualificationId(1L);
        qualification.setUser(user);
        qualification.setDegree("B.Tech");
        qualification.setInstitute("ABC Institute");
        qualification.setQualificationType("Graduation");
        qualification.setStartDate(LocalDate.of(2020, 1, 1));
        qualification.setEndDate(LocalDate.of(2024, 1, 1));
        qualification.setUrl("http://example.com");
    }

    @Test
    void testGetAllQualifications() throws Exception {
        List<Qualification> list = Arrays.asList(qualification);
        when(qualificationService.getAllQualifications()).thenReturn(list);

        mockMvc.perform(get("/api/qualifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].degree").value("B.Tech"));
    }

    @Test
    void testFindQualificationById() throws Exception {
        when(qualificationService.findQualificationById(1L)).thenReturn(qualification);

        mockMvc.perform(get("/api/qualifications/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.qualificationId").value(1L))
                .andExpect(jsonPath("$.degree").value("B.Tech"));
    }

    @Test
    void testCreateQualification() throws Exception {
        when(qualificationService.createQualification(any(Qualification.class))).thenReturn(qualification);

        mockMvc.perform(post("/api/qualifications")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(qualification)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.degree").value("B.Tech"));
    }

    @Test
    void testUpdateQualification() throws Exception {
        Qualification updated = new Qualification();
        updated.setQualificationId(1L);
        updated.setDegree("M.Tech");
        updated.setInstitute("XYZ Institute");
        updated.setQualificationType("Post-Graduation");
        updated.setStartDate(LocalDate.of(2024, 1, 1));
        updated.setEndDate(LocalDate.of(2026, 1, 1));
        updated.setUrl("http://xyz.com");
        updated.setUser(new User());

        when(qualificationService.updateQualification(eq(1L), any(Qualification.class))).thenReturn(updated);

        mockMvc.perform(put("/api/qualifications/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.degree").value("M.Tech"))
                .andExpect(jsonPath("$.institute").value("XYZ Institute"));
    }

    @Test
    void testDeleteQualification() throws Exception {
        doNothing().when(qualificationService).deleteQualification(1L);

        mockMvc.perform(delete("/api/qualifications/1"))
                .andExpect(status().isNoContent());
    }
}
