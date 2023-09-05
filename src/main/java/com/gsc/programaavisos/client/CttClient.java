package com.gsc.programaavisos.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "getCTTAddress", url = "${app.ctt.client.url}")
public interface CttClient {

    @GetMapping("/getCttInfo")
    ResponseEntity<String> getCttAddressInfo(@RequestHeader Map<String, String> header,
                                             @RequestParam("cp4") String cp4,
                                             @RequestParam("cp3") String cp3);
}
