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
    public ResponseEntity<List<PaParameterization>> searchParametrization(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                           @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
                                                                           @RequestParam(required = false) String selectedTypeParam) {
        log.info("searchParametrization controller");
        List<PaParameterization> paParameterizationList = parametrizationService.searchParametrization(startDate, endDate, selectedTypeParam, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(paParameterizationList);
    }

    @DeleteMapping(ApiEndpoints.DELETE_PARAMATRIZATION)
    public void deleteParametrization(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                          @RequestParam(required = false) Integer id ) {
        log.info("deleteParametrization controller");
        parametrizationService.deleteParametrization(userPrincipal,id);
    }

    @GetMapping(ApiEndpoints.GET_PARAMETRIZATION_LIST)
    public ResponseEntity<List<PaParameterization>> getParametrizationList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("getParametrizationList controller");
        List<PaParameterization> paParameterizationList = parametrizationService.getParametrizationsList(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(paParameterizationList);
    }

    @GetMapping(ApiEndpoints.GET_NEW_PARAMETRIZATION)
    public ResponseEntity<PaParameterization> getNewParametrization(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                          @RequestParam Integer id) {
        log.info("getParametrizationList controller");
        PaParameterization paParameterizationList = parametrizationService.getNewParametrization(userPrincipal,id);
        return ResponseEntity.status(HttpStatus.OK).body(paParameterizationList);
    }
}
