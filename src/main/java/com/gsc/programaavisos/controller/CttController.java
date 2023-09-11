package com.gsc.programaavisos.controller;


import com.gsc.programaavisos.service.CttService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
@Slf4j
@Api(value = "",tags = "CTT")
public class CttController {
    private final CttService cttService;

    @GetMapping("/ctt-address-info")
    @Operation(summary= "Endpoint to fetch address from the ctt service",
            description="Endpoint to fetch address from the ctt service")
    public ResponseEntity<String> getCttAddressInfo(@RequestParam String cp4, @RequestParam String cp3){
        log.info("getCttAddress controller{}{} ",cp4,cp3);
        return cttService.getCttAddressInfo(cp4, cp3);
    }
}
