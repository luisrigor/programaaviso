package com.gsc.programaavisos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gsc.programaavisos.config.SecurityConfig;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.repository.crm.ClientRepository;
import com.gsc.programaavisos.repository.crm.ConfigurationRepository;
import com.gsc.programaavisos.repository.crm.LoginKeyRepository;
import com.gsc.programaavisos.repository.crm.ServiceLoginRepository;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.TokenProvider;
import com.gsc.programaavisos.service.BackOfficeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, TokenProvider.class})
@ActiveProfiles(profiles = SecurityData.ACTIVE_PROFILE)
@WebMvcTest(BackOfficeController.class)
public class BackOfficeControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BackOfficeService backOfficeService;
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
        doNothing().when(backOfficeService).importTechnicalCampaign(any(),any());
        MockMultipartFile firstFile = new MockMultipartFile("file", "filename.txt", "text/plain", "some xml".getBytes());
        mvc.perform(MockMvcRequestBuilders.multipart(BASE_REQUEST_MAPPING+ ApiEndpoints.IMPORT_TECHNICAL_CAMPAIGN)
                        .file(firstFile)
                        .header("accessToken", accessToken))
                .andExpect(status().is(200))
                .andExpect(content().string("File is being processed at the end an email will be sent to "+SecurityData.getUserDefaultStatic().getEmail()));;
    }

}
