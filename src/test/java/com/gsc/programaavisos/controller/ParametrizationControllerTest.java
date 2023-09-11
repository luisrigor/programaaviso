package com.gsc.programaavisos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsc.programaavisos.config.SecurityConfig;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.ParameterizationDTO;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.repository.crm.ClientRepository;
import com.gsc.programaavisos.repository.crm.ConfigurationRepository;
import com.gsc.programaavisos.repository.crm.LoginKeyRepository;
import com.gsc.programaavisos.repository.crm.ServiceLoginRepository;
import com.gsc.programaavisos.sample.data.provider.ParametrizationData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.TokenProvider;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, TokenProvider.class})
@ActiveProfiles(profiles = SecurityData.ACTIVE_PROFILE)
@WebMvcTest(ParametrizationController.class)
class ParametrizationControllerTest {

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

    @Test
    void whenGetListParametrizationThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<PaParameterization> paParameterizationList = new ArrayList<>(Collections
                .singletonList(ParametrizationData.getPaParameterization()));
        when(parametrizationService.getParametrizationsList(any())).thenReturn(paParameterizationList);
        mvc.perform(get(BASE_REQUEST_MAPPING+ ApiEndpoints.GET_PARAMETRIZATION_LIST)
                        .header("accessToken", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(objectMapper.writeValueAsString(paParameterizationList)));
    }

    @Test
    void whenGetNewParametrizationThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        PaParameterization paParameterization = ParametrizationData.getPaParameterization();
        when(parametrizationService.getNewParametrization(any(),anyInt())).thenReturn(paParameterization);
        mvc.perform(get(BASE_REQUEST_MAPPING+ ApiEndpoints.GET_NEW_PARAMETRIZATION)
                        .header("accessToken", accessToken)
                        .queryParam("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(paParameterization)));
    }

    @Test
    void whenCloneParametrizationThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        doNothing().when(parametrizationService).cloneParameterization(any(),anyInt());
        mvc.perform(post(BASE_REQUEST_MAPPING+ ApiEndpoints.CLONE_PARAMETRIZATION)
                        .header("accessToken", accessToken)
                        .queryParam("id","1")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully Clone Parametrization"));
    }

    @Test
    void whenSaveParametrizationThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        ParameterizationDTO parameterizationDTO = new ParameterizationDTO();
        doNothing().when(parametrizationService).saveParameterization(any(),any());
        mvc.perform(post(BASE_REQUEST_MAPPING+ ApiEndpoints.SAVE_PARAMETRIZATION)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(parameterizationDTO))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Successfully Save Parametrization"));
    }

}
