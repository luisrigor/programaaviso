package com.gsc.programaavisos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsc.programaavisos.config.SecurityConfig;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ManageItemsDTO;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import com.gsc.programaavisos.repository.crm.ClientRepository;
import com.gsc.programaavisos.repository.crm.ConfigurationRepository;
import com.gsc.programaavisos.repository.crm.LoginKeyRepository;
import com.gsc.programaavisos.repository.crm.ServiceLoginRepository;
import com.gsc.programaavisos.sample.data.provider.ItemData;
import com.gsc.programaavisos.sample.data.provider.OtherFlowData;
import com.gsc.programaavisos.sample.data.provider.ParametrizationData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.TokenProvider;
import com.gsc.programaavisos.service.ItemService;
import com.gsc.programaavisos.service.ParametrizationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, TokenProvider.class})
@ActiveProfiles(profiles = SecurityData.ACTIVE_PROFILE)
@WebMvcTest(ParametrizationController.class)
public class ParametrizationControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ParametrizationService parametrizationService;
    @MockBean
    private ConfigurationRepository configurationRepository;
    @MockBean
    private ServiceLoginRepository serviceLoginRepository;
    @MockBean
    private LoginKeyRepository loginKeyRepository;
    @MockBean
    private EnvironmentConfig environmentConfig;
    @MockBean
    private ClientRepository clientRepository;
    private SecurityData securityData;
    private static String generatedToken;

    private final String BASE_REQUEST_MAPPING = "/avisos";

    @BeforeEach
    void setUp() {
        securityData = new SecurityData();
        when(loginKeyRepository.findById(anyLong())).thenReturn(Optional.of(securityData.getLoginKey()));
    }

    @BeforeAll
    static void beforeAll() {
        SecurityData secData = new SecurityData();
        generatedToken = secData.generateNewToken();
    }

    @Test
    void whenRequestPaParameterizationListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<PaParameterization> paParameterizationList = new ArrayList<>(Collections
                .singletonList(ParametrizationData.getPaParameterization()));
        when(parametrizationService.searchParametrization(any(),any(),any(),any())).thenReturn(paParameterizationList);
        mvc.perform(get(BASE_REQUEST_MAPPING+ ApiEndpoints.SEARCH_PARAMETRIZATIONS)
                        .header("accessToken", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(objectMapper.writeValueAsString(paParameterizationList)));
    }

    @Test
    void whenPaParameterizationDeleteSuccessfully() throws Exception {
        String accessToken = generatedToken;
        doNothing().when(parametrizationService).deleteParametrization(any(),any());
        mvc.perform(delete(BASE_REQUEST_MAPPING+ ApiEndpoints.DELETE_PARAMATRIZATION)
                        .header("accessToken", accessToken))
                .andExpect(status().isOk());
    }
/*
    @Test
    void whenDeletePaParameterizationDeleteSuccessfully() throws Exception {
        String accessToken = generatedToken;
        doThrow(ProgramaAvisosException.class).when(parametrizationService).deleteParametrization(any(),any());
        // Act & Assert
        mvc.perform(delete(BASE_REQUEST_MAPPING+ ApiEndpoints.DELETE_PARAMATRIZATION)
                        .header("accessToken", accessToken))
                .andExpect(status().isInternalServerError());
    }
 */


}
