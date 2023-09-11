package com.gsc.programaavisos.service.impl;

import com.gsc.programaavisos.client.CttClient;
import com.gsc.programaavisos.service.CttService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@Service
public class CttServiceImpl implements CttService {

    private final CttClient cttClient;


    @Override
    public ResponseEntity<String> getCttAddressInfo(String cp4, String cp3) {
        Map<String, String> headers = new HashMap<>();
        String accessT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbnZpcm9tZW50IjoyMCwiY29tcGFueSI6IlJpZ29yIn0.BXNRh9MvAp7KNxNjYF5WH1guWe9-IaRbvePO0ootHFc";
        headers.put("accessToken", accessT);

        return cttClient.getCttAddressInfo(headers, cp4, cp3);
    }
}
