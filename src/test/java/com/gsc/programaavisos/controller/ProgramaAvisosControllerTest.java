package com.gsc.programaavisos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gsc.programaavisos.config.SecurityConfig;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.repository.crm.ClientRepository;
import com.gsc.programaavisos.repository.crm.ConfigurationRepository;
import com.gsc.programaavisos.repository.crm.LoginKeyRepository;
import com.gsc.programaavisos.repository.crm.ServiceLoginRepository;
import com.gsc.programaavisos.sample.data.provider.ParametrizationData;
import com.gsc.programaavisos.sample.data.provider.ProgramaAvisosData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.TokenProvider;
import com.gsc.programaavisos.service.ParametrizationService;
import com.gsc.programaavisos.service.ProgramaAvisosService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, TokenProvider.class})
@ActiveProfiles(profiles = SecurityData.ACTIVE_PROFILE)
@WebMvcTest(ProgramaAvisosController.class)
public class ProgramaAvisosControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProgramaAvisosService programaAvisosService;
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
    private Gson gson;
    private SecurityData securityData;
    private static String generatedToken;

    private final String BASE_REQUEST_MAPPING = "/avisos";

    @BeforeEach
    void setUp() {
        gson = new Gson();
        securityData = new SecurityData();
        when(loginKeyRepository.findById(anyLong())).thenReturn(Optional.of(securityData.getLoginKey()));
    }

    @BeforeAll
    static void beforeAll() {
        SecurityData secData = new SecurityData();
        generatedToken = secData.generateNewToken();
    }

    @Test
    void whenRequestSearchPaThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        SearchPADTO searchPADTO = new SearchPADTO();
        FilterBean filterBean = ProgramaAvisosData.getFilterBean();
        when(programaAvisosService.searchPA(any(),any())).thenReturn(filterBean);
        mvc.perform(post(BASE_REQUEST_MAPPING+ ApiEndpoints.SEARCH_PA)
                        .header("accessToken", accessToken).contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(searchPADTO)))
                .andExpect(status().isOk())
                //.andExpect(content().contentType(MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8"))
                .andExpect(content().string(gson.toJson(filterBean)));
    }

    @Test
    void whenRequestSavePaThenItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        PADTO padto = new PADTO();
        doNothing().when(programaAvisosService).savePA(any(),any());
        mvc.perform(post(BASE_REQUEST_MAPPING+ ApiEndpoints.SAVE_PA)
                        .header("accessToken", accessToken).contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(padto)))
                .andExpect(status().isOk());
    }

    @Test
    void whenRequestRemovePaThenItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        doNothing().when(programaAvisosService).removePA(any(),any(),any(),any());
        mvc.perform(post(BASE_REQUEST_MAPPING+ ApiEndpoints.REMOVE_PA+"?id=1&removedObs=1&removedOption=1")
                        .header("accessToken", accessToken))
                .andExpect(status().isOk());
    }

    @Test
    void whenRequestUnlockPARegisterThenItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        doNothing().when(programaAvisosService).unlockPARegister(anyInt());
        mvc.perform(put(BASE_REQUEST_MAPPING+ ApiEndpoints.UNLOCK_PA)
                        .header("accessToken", accessToken)
                        .queryParam("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().string("unlocked"));
    }

    @Test
    void whenRequestPaInfoListThenItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        PAInfoDTO paInfoDTO = new PAInfoDTO();
        when(programaAvisosService.getInfoPA(any())).thenReturn(paInfoDTO);
        mvc.perform(get(BASE_REQUEST_MAPPING+ ApiEndpoints.LIST_PA)
                        .header("accessToken", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().string(gson.toJson(paInfoDTO)));
    }

    @Test
    void whenRequestGetPaDetailThenItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        DetailsPADTO detailsPADTO = new DetailsPADTO();
        when(programaAvisosService.getPaDetail(any(),anyInt(),anyInt())).thenReturn(detailsPADTO);
        mvc.perform(get(BASE_REQUEST_MAPPING+ ApiEndpoints.GET_PA_DETAIL)
                        .header("accessToken", accessToken)
                        .queryParam("id","1")
                        .queryParam("oldId","1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(detailsPADTO)));
    }

    @Test
    void whenRequestGetTPASimulationThenItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        TpaSimulation tpaSimulation = new TpaSimulation();
        TpaDTO tpaDTO = new TpaDTO();
        when(programaAvisosService.getTpaSimulation(any(),any())).thenReturn(tpaSimulation);
        mvc.perform(get(BASE_REQUEST_MAPPING+ ApiEndpoints.GET_TPA_SIMULATOR)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tpaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(tpaSimulation)));
    }

    @Test
    void whenRequestActivePAThenItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        doNothing().when(programaAvisosService).activatePA(any(),anyInt());
        mvc.perform(put(BASE_REQUEST_MAPPING+ ApiEndpoints.ACTIVE_PA)
                        .header("accessToken", accessToken)
                        .queryParam("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().string("activePA"));
    }
}
