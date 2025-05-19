package com.capgemini.job_application;

import com.capgemini.job_application.entities.Qualification;
import com.capgemini.job_application.repositories.QualificationRepository;
import com.capgemini.job_application.services.QualificationServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QualificationServiceImplTest {

    @Mock
    private QualificationRepository qualificationRepository;

    @InjectMocks
    private QualificationServiceImpl qualificationService;

    private Qualification qualification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        qualification = new Qualification();
        qualification.setQualificationId(1L);
        qualification.setQualificationType("B.Tech");
    }

    @Test
    void testGetAllQualifications() {
        when(qualificationRepository.findAll()).thenReturn(Arrays.asList(qualification));

        List<Qualification> result = qualificationService.getAllQualifications();

        assertEquals(1, result.size());
        assertEquals("B.Tech", result.get(0).getQualificationType());
        verify(qualificationRepository, times(1)).findAll();
    }

    @Test
    void testFindQualificationById() {
        when(qualificationRepository.findById(1L)).thenReturn(Optional.of(qualification));

        Qualification result = qualificationService.findQualificationById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getQualificationId());
        verify(qualificationRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateQualification() {
        when(qualificationRepository.save(qualification)).thenReturn(qualification);

        Qualification result = qualificationService.createQualification(qualification);

        assertNotNull(result);
        assertEquals("B.Tech", result.getQualificationType());
        verify(qualificationRepository, times(1)).save(qualification);
    }

    @Test
    void testUpdateQualification() {
        Qualification updated = new Qualification();
        updated.setQualificationType("M.Tech");

        when(qualificationRepository.findById(1L)).thenReturn(Optional.of(qualification));
        when(qualificationRepository.save(any(Qualification.class))).thenReturn(updated);

        Qualification result = qualificationService.updateQualification(1L, updated);

        assertNotNull(result);
        assertEquals("M.Tech", result.getQualificationType());
        verify(qualificationRepository, times(1)).save(any(Qualification.class));
    }

    @Test
    void testPatchQualification() {
        Qualification partialUpdate = new Qualification();
        partialUpdate.setQualificationType("Diploma");

        when(qualificationRepository.findById(1L)).thenReturn(Optional.of(qualification));
        when(qualificationRepository.save(any(Qualification.class))).thenReturn(partialUpdate);

        Qualification result = qualificationService.patchQualification(1L, partialUpdate);

        assertNotNull(result);
        assertEquals("Diploma", result.getQualificationType());
        verify(qualificationRepository).save(any(Qualification.class));
    }

    @Test
    void testDeleteQualification() {
        when(qualificationRepository.existsById(1L)).thenReturn(true); 
        doNothing().when(qualificationRepository).deleteById(1L);

        qualificationService.deleteQualification(1L);

        verify(qualificationRepository, times(1)).existsById(1L);
        verify(qualificationRepository, times(1)).deleteById(1L);
    }

}