package com.gsc.programaavisos.controller;


import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.ContactReason;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ProgramaAvisosService;
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
@Api(value = "", tags = "AVISOS")
@RestController
@CrossOrigin("*")
public class ProgramaAvisosController {

    private final ProgramaAvisosService programaAvisosService;

    @GetMapping(ApiEndpoints.SEARCH_PARAMETRIZATIONS)
    public ResponseEntity<List<PaParameterization>> searchParametrizations(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                         @RequestParam(required = false) Date startDate, @RequestParam(required = false) Date endDate,
                                                         @RequestParam(required = false) String selectedTypeParam) {
        List<PaParameterization> paParameterizations = programaAvisosService.searchParametrizations(startDate, endDate, selectedTypeParam, userPrincipal);

        return ResponseEntity.status(HttpStatus.OK).body(paParameterizations);
    }

    @GetMapping(ApiEndpoints.GET_MODELS)
    public ResponseEntity<List<Modelo>> getModels(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Modelo> models = programaAvisosService.getModels(userPrincipal);

        return ResponseEntity.status(HttpStatus.OK).body(models);
    }

    @GetMapping(ApiEndpoints.GET_CONTACT_REASONS)
    public ResponseEntity<List<ContactReason>> getContactReasons() {
        List<ContactReason> contactReasons = programaAvisosService.getContactReasons();

        return ResponseEntity.status(HttpStatus.OK).body(contactReasons);
    }


}
