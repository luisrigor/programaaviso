package com.gsc.programaavisos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gsc.programaavisos.config.SecurityConfig;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.DelegatorsDTO;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.GetDelegatorsDTO;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.crm.ClientRepository;
import com.gsc.programaavisos.repository.crm.ConfigurationRepository;
import com.gsc.programaavisos.repository.crm.LoginKeyRepository;
import com.gsc.programaavisos.repository.crm.ServiceLoginRepository;
import com.gsc.programaavisos.sample.data.provider.OtherFlowData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.TokenProvider;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.OtherFlowService;
import com.rg.dealer.Dealer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import({SecurityConfig.class, TokenProvider.class})
@ActiveProfiles(profiles = SecurityData.ACTIVE_PROFILE)
@WebMvcTest(OtherFlowController.class)
public class OtherFlowControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private OtherFlowService otherFlowService;
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
    void whenRequestModelListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Modelo> models = new ArrayList<>(Collections.singletonList(OtherFlowData.getModelo()));
        when(otherFlowService.getModels(any())).thenReturn(models);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_MODELS)
                        .header("accessToken", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(objectMapper.writeValueAsString(models)));
    }

    @Test
    void whenRequestFuelListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Fuel> fuels = new ArrayList<>(Collections.singletonList(OtherFlowData.getFuel()));
        when(otherFlowService.getFuels(any())).thenReturn(fuels);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_FUELS)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(fuels)));
    }

    @Test
    void whenRequestContactReasonsListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<ContactReason> contactReasonList = new ArrayList<>(Collections.singletonList(OtherFlowData.getContactReason()));
        when(otherFlowService.getContactReasons()).thenReturn(contactReasonList);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_CONTACT_REASONS)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(contactReasonList)));
    }

    @Test
    void whenRequestGenreListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Genre> genreList = new ArrayList<>(Collections.singletonList(OtherFlowData.getGenre()));
        when(otherFlowService.getGenre()).thenReturn(genreList);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_GENRE)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(genreList)));
    }

    @Test
    void whenRequestKilometersListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Kilometers> kilometers = new ArrayList<>(Collections.singletonList(OtherFlowData.getKilometers()));
        when(otherFlowService.getKilometers()).thenReturn(kilometers);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_KILOMENTERS)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(kilometers)));
    }


    @Test
    void whenRequestEntityListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<EntityType> entities = new ArrayList<>(Collections.singletonList(OtherFlowData.getEntity()));
        when(otherFlowService.getEntityType()).thenReturn(entities);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_ENTITY_TYPE)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(entities)));
    }

    @Test
    void whenRequestAgeListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Age> ageList = new ArrayList<>(Collections.singletonList(OtherFlowData.getAge()));
        when(otherFlowService.getAge()).thenReturn(ageList);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_AGE)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(ageList)));
    }

    @Test
    void whenRequestFidelityListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Fidelitys> fidelitysList = new ArrayList<>(Collections.singletonList(OtherFlowData.getFidelity()));
        when(otherFlowService.getFidelitys()).thenReturn(fidelitysList);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_FIDELITYS)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(fidelitysList)));
    }

    @Test
    void whenRequestDocumentListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<DocumentUnitDTO> documentList = new ArrayList<>(Collections.singletonList(OtherFlowData.getDocumentUnit()));
        when(otherFlowService.searchDocumentUnit(any(),any())).thenReturn(documentList);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_DOCUMENT_UNIT)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(documentList)));
    }

    @Test
    void whenRequestDealersListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Dealer> dealers = new ArrayList<>(Collections.singletonList(OtherFlowData.getDealer()));
        when(otherFlowService.getDealers(any())).thenReturn(dealers);
        mvc.perform(get(BASE_REQUEST_MAPPING+ApiEndpoints.GET_DEALERS)
                        .header("accessToken", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.TEXT_PLAIN+";charset=UTF-8"));
    }

    @Test
    void whenRequestDelegatorsThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        GetDelegatorsDTO getDelegatorsDTO = new GetDelegatorsDTO();
        DelegatorsDTO delegatorsDTOS = OtherFlowData.getDelegators();
        when(otherFlowService.getDelegators(any(), any())).thenReturn(delegatorsDTOS);
        mvc.perform(post(BASE_REQUEST_MAPPING + ApiEndpoints.GET_DELEGATORS)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDelegatorsDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(delegatorsDTOS)));
    }
}
