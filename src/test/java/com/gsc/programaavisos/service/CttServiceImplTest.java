package com.gsc.programaavisos.service;

import com.gsc.programaavisos.client.CttClient;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.service.impl.CttServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import static org.mockito.ArgumentMatchers.*;

import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
 class CttServiceImplTest {
    @Mock
    private CttClient cttClient;
    @InjectMocks
    private CttServiceImpl cttService;
    private SecurityData securityData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityData = new SecurityData();
    }

    @Test
    void getCttAddressInfoSuccessfullyCase() {
        // Arrange
        ResponseEntity<String> mockResponse = new ResponseEntity<>("mockResponse", HttpStatus.OK);
        when(cttClient.getCttAddressInfo(anyMap(),anyString(),anyString())).thenReturn(mockResponse);
        // Act
        ResponseEntity<String> ctt = cttService.getCttAddressInfo("cp4","cp3");
        // Assert
        Assertions.assertEquals(mockResponse,ctt);
    }

}
