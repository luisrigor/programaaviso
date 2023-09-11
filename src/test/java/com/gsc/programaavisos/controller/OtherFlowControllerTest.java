package com.gsc.programaavisos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gsc.programaavisos.config.SecurityConfig;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.crm.ClientRepository;
import com.gsc.programaavisos.repository.crm.ConfigurationRepository;
import com.gsc.programaavisos.repository.crm.LoginKeyRepository;
import com.gsc.programaavisos.repository.crm.ServiceLoginRepository;
import com.gsc.programaavisos.sample.data.provider.ItemData;
import com.gsc.programaavisos.sample.data.provider.OtherFlowData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.TokenProvider;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.OtherFlowService;
import com.rg.dealer.Dealer;
import com.sun.mail.imap.protocol.Item;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
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

    @Test
    void whenGetContactTypeThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<ContactType> contactTypeList = Collections.singletonList(OtherFlowData.getContactType());
        when(otherFlowService.getContactTypeList(anyString())).thenReturn(contactTypeList);
        mvc.perform(get(BASE_REQUEST_MAPPING + ApiEndpoints.GET_CONTACT_TYPE)
                        .header("accessToken", accessToken)
                        .param("userLogin","anyInput"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(contactTypeList)));
    }

    @Test
    void whenGetChangedListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Object[]> changedList = new ArrayList<>();
        GetDelegatorsDTO getDelegatorsDTO = new GetDelegatorsDTO();
        when(otherFlowService.getChangedList(any())).thenReturn(changedList);
        mvc.perform(post(BASE_REQUEST_MAPPING + ApiEndpoints.GET_CHANGED_LIST)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(getDelegatorsDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(changedList)));
    }

    @Test
    void whenGetPAClientContactsThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<ClientPropDTO> contactsForPlate = Collections.singletonList(OtherFlowData.getClientPropDTO());
        List<ClientPropDTO> contactsForClient = Collections.singletonList(OtherFlowData.getClientPropDTO());
        ClientContactsDTO clientContacts = ClientContactsDTO.builder()
                .contactsForClient(contactsForClient)
                .contactsForPlate(contactsForPlate).build();
        FilterBean filterBean = FilterBean.builder().build();

        when(otherFlowService.getPAClientContacts(anyString(),anyString(),anyInt(),any(FilterBean.class)))
                .thenReturn(clientContacts);
        mvc.perform(post(BASE_REQUEST_MAPPING + ApiEndpoints.GET_PA_CLIENT_CONTACTS)
                        .header("accessToken", accessToken)
                        .queryParam("nif","anyNif")
                        .queryParam("selPlate","anyPlate")
                        .queryParam("idPaData","1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filterBean)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(clientContacts)));
    }

    @Test
    void whenMapUpdateItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        doNothing().when(otherFlowService).mapUpdate(any(),any());
        mvc.perform(post(BASE_REQUEST_MAPPING + ApiEndpoints.MAP_UPDATE)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Update"));
    }

    @Test
    void whenGetClientTypesThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<ClientType> clientTypes = new ArrayList<>();
        when(otherFlowService.getClientTypes()).thenReturn(clientTypes);
        mvc.perform(get(BASE_REQUEST_MAPPING + ApiEndpoints.GET_CLIENT_TYPE)
                        .header("accessToken", accessToken)
                        .param("userLogin","anyInput"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(clientTypes)));
    }

    @Test
    void whenGetChannelsThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Channel> channels = new ArrayList<>();
        when(otherFlowService.getChannels()).thenReturn(channels);
        mvc.perform(get(BASE_REQUEST_MAPPING + ApiEndpoints.GET_CHANNEL)
                        .header("accessToken", accessToken)
                        .param("userLogin","anyInput"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(channels)));
    }

    @Test
    void whenGetSourcesThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<Source> sources = new ArrayList<>();
        when(otherFlowService.getSources()).thenReturn(sources);
        mvc.perform(get(BASE_REQUEST_MAPPING + ApiEndpoints.GET_SOURCE)
                        .header("accessToken", accessToken)
                        .param("userLogin","anyInput"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(sources)));
    }

    @Test
    void whenGetContactTypeListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<ContactType> contactTypes = new ArrayList<>();
        when(otherFlowService.getAllContactTypes()).thenReturn(contactTypes);
        mvc.perform(get(BASE_REQUEST_MAPPING + ApiEndpoints.GET_ALL_CONTACT_TYPE)
                        .header("accessToken", accessToken)
                        .param("userLogin","anyInput"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(contactTypes)));
    }

    @Test
    void whenGetMaintenanceTypesListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<MaintenanceTypeDTO> maintenanceTypes = new ArrayList<>();
        when(otherFlowService.getMaintenanceTypes()).thenReturn(maintenanceTypes);
        mvc.perform(get(BASE_REQUEST_MAPPING + ApiEndpoints.GET_MAIN_TYPE)
                        .header("accessToken", accessToken)
                        .param("userLogin","anyInput"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(maintenanceTypes)));
    }

    @Test
    void whenDownloadSimulationThenItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<MaintenanceTypeDTO> maintenanceTypes = new ArrayList<>();
        doNothing().when(otherFlowService).downloadSimulation(any(),any(),any());
        mvc.perform(post(BASE_REQUEST_MAPPING + ApiEndpoints.DOWNLOAD_SIMULATION)
                        .header("accessToken", accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new TpaSimulation()))
                        )
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void whenSendNewsletterThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        NewsLetterDTO newsletter = new NewsLetterDTO();
        when(otherFlowService.sendNewsletter(anyInt(),anyString())).thenReturn(newsletter);
        mvc.perform(get(BASE_REQUEST_MAPPING + ApiEndpoints.SEND_NEWSLETTER)
                        .header("accessToken", accessToken)
                        .queryParam("id","1")
                        .queryParam("email","anyEmail")
                )
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(newsletter)));
    }
}
