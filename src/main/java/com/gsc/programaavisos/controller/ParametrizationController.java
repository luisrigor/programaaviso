package com.gsc.programaavisos.controller;

import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ParametrizationService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@Log4j
@RequestMapping("${app.baseUrl}")
@Api(value = "", tags = "PARAMETRIZATION")
@RestController
@CrossOrigin("*")
public class ParametrizationController {

    private final ParametrizationService parametrizationService;

    @GetMapping(ApiEndpoints.SEARCH_PARAMETRIZATIONS)
    public ResponseEntity<List<PaParameterization>> searchParametrizations(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                           @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
                                                                           @RequestParam(required = false) String selectedTypeParam) {
        log.info("searchParametrization controller");
        List<PaParameterization> paParameterizationList = parametrizationService.searchParametrizations(startDate, endDate, selectedTypeParam, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(paParameterizationList);
    }
}
