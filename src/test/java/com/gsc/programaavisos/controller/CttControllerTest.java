package com.gsc.programaavisos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.gsc.programaavisos.config.SecurityConfig;
import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.DetailsPADTO;
import com.gsc.programaavisos.repository.crm.ClientRepository;
import com.gsc.programaavisos.repository.crm.ConfigurationRepository;
import com.gsc.programaavisos.repository.crm.LoginKeyRepository;
import com.gsc.programaavisos.repository.crm.ServiceLoginRepository;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.TokenProvider;
import com.gsc.programaavisos.service.CttService;
import com.gsc.programaavisos.service.ProgramaAvisosService;
import io.swagger.v3.oas.annotations.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({SecurityConfig.class, TokenProvider.class})
@ActiveProfiles(profiles = SecurityData.ACTIVE_PROFILE)
@WebMvcTest(CttController.class)
public class CttControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CttService cttService;
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
    void whenRequestGetCttAddressInfoThenItsSuccessfully() throws Exception {
        String accessToken = generatedToken;
        ResponseEntity<String> responseEntity = new ResponseEntity<>("CttAddressInfo", HttpStatus.OK);
        when(cttService.getCttAddressInfo(anyString(), anyString())).thenReturn(responseEntity);
        mvc.perform(get("/ctt-address-info")
                        .header("accessToken", accessToken)
                        .queryParam("cp4", "anyCp4")
                        .queryParam("cp3", "anyCp3"))
                .andExpect(status().isOk())
                .andExpect(content().string("CttAddressInfo"));
    }

}
