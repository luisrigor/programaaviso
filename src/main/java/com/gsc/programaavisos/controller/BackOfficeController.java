package com.gsc.programaavisos.controller;


import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.BackOfficeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Log4j
@RequestMapping("${app.baseUrl}")
@Api(value = "", tags = "Back-Office")
@RestController
@CrossOrigin("*")
public class BackOfficeController {

    private final BackOfficeService backOfficeService;

    @PostMapping(ApiEndpoints.IMPORT_TECHNICAL_CAMPAIGN)
    public ResponseEntity<String> importTechnicalCampaign(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestPart MultipartFile file) {
        backOfficeService.importTechnicalCampaign(userPrincipal, file);

        return ResponseEntity.status(HttpStatus.OK).body("File is being processed at the end an email will be sent to "+userPrincipal.getEmail());
    }
}
