package com.gsc.programaavisos.service;

import org.springframework.http.ResponseEntity;

public interface CttService {
    ResponseEntity<String> getCttAddressInfo(String cp4, String cp3);

}
