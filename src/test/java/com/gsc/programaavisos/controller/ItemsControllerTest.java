package com.gsc.programaavisos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsc.programaavisos.config.SecurityConfig;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ManageItemsDTO;
import com.gsc.programaavisos.repository.crm.ClientRepository;
import com.gsc.programaavisos.repository.crm.ConfigurationRepository;
import com.gsc.programaavisos.repository.crm.LoginKeyRepository;
import com.gsc.programaavisos.repository.crm.ServiceLoginRepository;
import com.gsc.programaavisos.sample.data.provider.ItemData;
import com.gsc.programaavisos.sample.data.provider.OtherFlowData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.TokenProvider;
import com.gsc.programaavisos.service.ItemService;
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

import java.sql.Date;
import java.util.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, TokenProvider.class})
@ActiveProfiles(profiles = SecurityData.ACTIVE_PROFILE)
@WebMvcTest(ItemsController.class)
class ItemsControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ItemService itemService;
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
    void whenRequestDocumentUnitDTOListThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<DocumentUnitDTO> documentList = new ArrayList<>(Collections.singletonList(OtherFlowData.getDocumentUnit()));
        when(itemService.searchItems(anyString(),any(Date.class),anyInt(),any())).thenReturn(documentList);
        mvc.perform(get(BASE_REQUEST_MAPPING+ ApiEndpoints.GET_SEARCH_ITEMS+"?searchInput=string&startDate=2023-08-10&tpaItemType=1")
                        .header("accessToken", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(objectMapper.writeValueAsString(documentList)));
    }

    @Test
    void whenRequestManageItemsDTOThenItsReturnSuccessfully() throws Exception {
        String accessToken = generatedToken;
        ManageItemsDTO manageItemsDTO = ItemData.getManageItemsDTO();
        when(itemService.getManageItems(any(),anyInt(),anyInt())).thenReturn(manageItemsDTO);
        mvc.perform(get(BASE_REQUEST_MAPPING+ ApiEndpoints.GET_MANAGE_ITEMS+"?itemId=1&itemType=1")
                        .header("accessToken", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(objectMapper.writeValueAsString(manageItemsDTO)));
    }

    @Test
    void whenGetItemListSuccessfully() throws Exception {
        String accessToken = generatedToken;
        List<DocumentUnitDTO> documentUnitDTOList = Collections.singletonList(ItemData.getDocumentUnitDTO());
        when(itemService.getListManagesItems(any(),anyString(),anyInt())).thenReturn(documentUnitDTOList);
        mvc.perform(get(BASE_REQUEST_MAPPING+ ApiEndpoints.GET_MANAGE_LIST)
                        .param("searchInput","anyInput")
                        .param("itemType","1")
                        .header("accessToken", accessToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().string(objectMapper.writeValueAsString(documentUnitDTOList)));
    }

}
