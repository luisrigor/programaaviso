package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.repository.crm.PaParameterizationRepository;
import com.gsc.programaavisos.sample.data.provider.ParametrizationData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.service.impl.ParametrizationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
class ParametrizationServiceImplTest {

    @Mock
    private PaParameterizationRepository paParameterizationRepository;
    @InjectMocks
    private ParametrizationServiceImpl parametrizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenSearchParameterizationThenReturnSuccessfully() {
        // Arrange
        List<PaParameterization> expectedParameterization = new ArrayList<>(Collections.singletonList(ParametrizationData.getPaParameterization()));
        when(paParameterizationRepository.getByFilter(any())).thenReturn(expectedParameterization);
        // Act
        List<PaParameterization> actualParameterization = parametrizationService.searchParametrization(Date.valueOf(LocalDate.MIN),
                Date.valueOf(LocalDate.MIN), "", SecurityData.getUserDefaultStatic());
        // Assert
        Assertions.assertEquals(expectedParameterization, actualParameterization);
    }

    @Test
    void whenSearchParameterizationThenThrowProgramaAvisosException() {
        //Arrange
        when(paParameterizationRepository.getByFilter(any(ParameterizationFilter.class))).thenThrow(RuntimeException.class);

        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, () -> parametrizationService.searchParametrization(Date.valueOf(LocalDate.MIN),
                Date.valueOf(LocalDate.MIN), "", SecurityData.getUserDefaultStatic()));
    }

    @Test
    void whenSearchParameterizationAndTypeParamIsNotEmptyThenReturnSuccessfully() {
        //Arrange
        List<PaParameterization> expectedParameterization = new ArrayList<>(Collections.singletonList(ParametrizationData.getPaParameterization()));
        when(paParameterizationRepository.getByFilter(any())).thenReturn(expectedParameterization);
        // Act
        List<PaParameterization> actualParameterization = parametrizationService.searchParametrization(Date.valueOf(LocalDate.MIN),
                Date.valueOf(LocalDate.MIN), "selectType", SecurityData.getUserDefaultStatic());
        // Assert
        Assertions.assertEquals(expectedParameterization, actualParameterization);
    }

    @Test
    void whenDeleteParameterizationThenReturnSuccessfully() {
        // Arrange
        doNothing().when(paParameterizationRepository).deleteById(anyInt());
        // Act
        parametrizationService.deleteParametrization(SecurityData.getUserDefaultStatic(), 1);
        // Assert
        verify(paParameterizationRepository, times(1)).deleteById(1);
    }

    @Test
    void whenDeleteParameterizationThenThrowProgramaAvisosException() {
        //Arrange
        doThrow(ProgramaAvisosException.class).when(paParameterizationRepository).deleteById(anyInt());
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class,
                () -> parametrizationService.deleteParametrization(SecurityData.getUserDefaultStatic(), 1));
    }

    @Test
    void whenGetParametrizationsListThenReturnSuccessfully() {
        //Arrange
        List<PaParameterization> expectedParameterization = new ArrayList<>(Collections.singletonList(ParametrizationData.getPaParameterization()));
        when(paParameterizationRepository.getByFilter(any())).thenReturn(expectedParameterization);
        // Act
        List<PaParameterization> actualParameterization = parametrizationService.getParametrizationsList(SecurityData.getUserDefaultStatic());
        // Assert
        Assertions.assertEquals(expectedParameterization, actualParameterization);
    }

    @Test
    void whenGetParametrizationsListThenThrowProgramaAvisosException() {
        //Arrange
        when(paParameterizationRepository.getByFilter(any())).thenThrow(ProgramaAvisosException.class);
        // Act
        Assertions.assertThrows(ProgramaAvisosException.class,
                () -> parametrizationService.getParametrizationsList(SecurityData.getUserDefaultStatic()));
    }

}