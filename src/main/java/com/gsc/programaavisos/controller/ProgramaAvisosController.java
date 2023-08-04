package com.gsc.programaavisos.controller;


import com.google.gson.Gson;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.dto.PADTO;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ProgramaAvisosService;
import com.rg.dealer.Dealer;
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
        log.info("searchParametrizations controller");
        List<PaParameterization> paParameterizations = programaAvisosService.searchParametrizations(startDate, endDate, selectedTypeParam, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(paParameterizations);
    }

    @GetMapping(ApiEndpoints.GET_MODELS)
    public ResponseEntity<List<Modelo>> getModels(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("getModels controller");
        List<Modelo> models = programaAvisosService.getModels(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(models);
    }

    @GetMapping(ApiEndpoints.GET_FUELS)
    public ResponseEntity<List<Fuel>> getFuels(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Fuel> fuels = programaAvisosService.getFuels(userPrincipal);

        return ResponseEntity.status(HttpStatus.OK).body(fuels);
    }

    @GetMapping(ApiEndpoints.GET_DOCUMENT_UNITS)
    public ResponseEntity<?> getDocumentUnits(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        programaAvisosService.getDocumentUnits(userPrincipal, 1);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping(ApiEndpoints.GET_CONTACT_REASONS)
    public ResponseEntity<List<ContactReason>> getContactReasons() {
        log.info("getContactReasons controller");
        List<ContactReason> contactReasons = programaAvisosService.getContactReasons();
        return ResponseEntity.status(HttpStatus.OK).body(contactReasons);
    }

    @GetMapping(ApiEndpoints.GET_GENRE)
    public ResponseEntity<List<Genre>> getGenre() {
        log.info("getGenre controller");
        List<Genre> genre = programaAvisosService.getGenre();
        return ResponseEntity.status(HttpStatus.OK).body(genre);
    }

    @GetMapping(ApiEndpoints.GET_KILOMENTERS)
    public ResponseEntity<List<Kilometers>> getKilometers() {
        log.info("getKilometers controller");
        List<Kilometers> kilometers = programaAvisosService.getKilometers();
        return ResponseEntity.status(HttpStatus.OK).body(kilometers);
    }

    @GetMapping(ApiEndpoints.GET_ENTITY_TYPE)
    public ResponseEntity<List<EntityType>> getEntityType() {
        log.info("getEntityType controller");
        List<EntityType> entityType = programaAvisosService.getEntityType();
        return ResponseEntity.status(HttpStatus.OK).body(entityType);
    }

    @GetMapping(ApiEndpoints.GET_AGE)
    public ResponseEntity<List<Age>> getAge() {
        log.info("getAge controller");
        List<Age> age = programaAvisosService.getAge();
        return ResponseEntity.status(HttpStatus.OK).body(age);
    }

    @GetMapping(ApiEndpoints.GET_FIDELITYS)
    public ResponseEntity<List<Fidelitys>> getFidelitys() {
        log.info("getFidelitys controller");
        List<Fidelitys> fidelitys = programaAvisosService.getFidelitys();
        return ResponseEntity.status(HttpStatus.OK).body(fidelitys);
    }
    @GetMapping(ApiEndpoints.GET_DOCUMENT_UNIT)
    public ResponseEntity<List<DocumentUnitDTO>> getDocumentUnit(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                              @RequestParam(required = false) Integer type) {
        log.info("getDocumentUnit controller");
        List<DocumentUnitDTO> paDocumentUnit = programaAvisosService.searchDocumentUnit(type, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(paDocumentUnit);
    }

    @GetMapping(ApiEndpoints.GET_SEARCH_ITEMS)
    public ResponseEntity<List<DocumentUnitDTO>> searchItems(@RequestParam(required = false) String searchInput,
                                                          @RequestParam(required = false) Date startDate,
                                                          @RequestParam(required = false) Integer tpaItemType,
                                                          @AuthenticationPrincipal UserPrincipal userPrincipal,
                                                          @RequestParam(required = true) Integer type) {
        log.info("searchItems controller");
        List<DocumentUnitDTO> items = programaAvisosService.searchItems(searchInput,startDate,tpaItemType, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping(ApiEndpoints.GET_DEALERS)
    public ResponseEntity<?> getDealers(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Dealer> dealers = programaAvisosService.getDealers(userPrincipal);
        Gson gson = new Gson();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dealers));
    }

    @GetMapping(ApiEndpoints.GET_MANAGE_ITEMS)
    public ResponseEntity<?> getManageItems(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Dealer> dealers = programaAvisosService.getManageItems(userPrincipal);
        Gson gson = new Gson();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dealers));
    }

    @PostMapping(ApiEndpoints.SAVE_PA)
    public void savePA(@AuthenticationPrincipal UserPrincipal userPrincipal,
                       @RequestBody PADTO padto) {
        log.info("savePA controller");
        programaAvisosService.savePA(userPrincipal,padto);
    }

    @PostMapping(ApiEndpoints.REMOVE_PA)
    public void removePA(@AuthenticationPrincipal UserPrincipal userPrincipal,
                         @RequestParam Integer id,
                         @RequestParam String removedOption,
                         @RequestParam String removedObs) {
        log.info("removePA controller");
        programaAvisosService.removePA(userPrincipal,id,removedOption,removedObs);
    }
}
