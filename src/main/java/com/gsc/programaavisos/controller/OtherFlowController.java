package com.gsc.programaavisos.controller;

import com.google.gson.Gson;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.DelegatorsDTO;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.GetDelegatorsDTO;
import com.gsc.programaavisos.dto.MaintenanceTypeDTO;
import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.OtherFlowService;
import com.rg.dealer.Dealer;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Log4j
@RequestMapping("${app.baseUrl}")
@Api(value = "", tags = "OTHER_FLOWS")
@RestController
@CrossOrigin("*")
public class OtherFlowController {

    private final OtherFlowService otherFlowService;


    @GetMapping(ApiEndpoints.GET_MODELS)
    public ResponseEntity<List<Modelo>> getModels(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("getModels controller");
        List<Modelo> models = otherFlowService.getModels(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(models);
    }

    @GetMapping(ApiEndpoints.GET_FUELS)
    public ResponseEntity<List<Fuel>> getFuels(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        List<Fuel> fuels = otherFlowService.getFuels(userPrincipal);

        return ResponseEntity.status(HttpStatus.OK).body(fuels);
    }

    @GetMapping(ApiEndpoints.GET_CONTACT_REASONS)
    public ResponseEntity<List<ContactReason>> getContactReasons() {
        log.info("getContactReasons controller");
        List<ContactReason> contactReasons = otherFlowService.getContactReasons();
        return ResponseEntity.status(HttpStatus.OK).body(contactReasons);
    }

    @GetMapping(ApiEndpoints.GET_GENRE)
    public ResponseEntity<List<Genre>> getGenre() {
        log.info("getGenre controller");
        List<Genre> genre = otherFlowService.getGenre();
        return ResponseEntity.status(HttpStatus.OK).body(genre);
    }

    @GetMapping(ApiEndpoints.GET_KILOMENTERS)
    public ResponseEntity<List<Kilometers>> getKilometers() {
        log.info("getKilometers controller");
        List<Kilometers> kilometers = otherFlowService.getKilometers();
        return ResponseEntity.status(HttpStatus.OK).body(kilometers);
    }

    @GetMapping(ApiEndpoints.GET_ENTITY_TYPE)
    public ResponseEntity<List<EntityType>> getEntityType() {
        log.info("getEntityType controller");
        List<EntityType> entityType = otherFlowService.getEntityType();
        return ResponseEntity.status(HttpStatus.OK).body(entityType);
    }

    @GetMapping(ApiEndpoints.GET_AGE)
    public ResponseEntity<List<Age>> getAge() {
        log.info("getAge controller");
        List<Age> age = otherFlowService.getAge();
        return ResponseEntity.status(HttpStatus.OK).body(age);
    }

    @GetMapping(ApiEndpoints.GET_FIDELITYS)
    public ResponseEntity<List<Fidelitys>> getFidelitys() {
        log.info("getFidelity controller");
        List<Fidelitys> fidelityList = otherFlowService.getFidelitys();
        return ResponseEntity.status(HttpStatus.OK).body(fidelityList);
    }
    @GetMapping(ApiEndpoints.GET_DOCUMENT_UNIT)
    public ResponseEntity<List<DocumentUnitDTO>> getDocumentUnit(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                                 @RequestParam(required = false) Integer type) {
        log.info("getDocumentUnit controller");
        List<DocumentUnitDTO> paDocumentUnit = otherFlowService.searchDocumentUnit(type, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(paDocumentUnit);
    }

    @GetMapping(ApiEndpoints.GET_DEALERS)
    public ResponseEntity<?> getDealers(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("getDealers controller");
        List<Dealer> dealers = otherFlowService.getDealers(userPrincipal);
        Gson gson = new Gson();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(dealers));
    }

    @PostMapping(ApiEndpoints.GET_DELEGATORS)
    public ResponseEntity<DelegatorsDTO> getDelegators(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                           @RequestBody GetDelegatorsDTO delegatorsDTO) {
        DelegatorsDTO rs = otherFlowService.getDelegators(userPrincipal, delegatorsDTO);

        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @GetMapping(ApiEndpoints.GET_CONTACT_TYPE)
    public ResponseEntity<List<ContactType>> getContactType(@RequestParam String userLogin) {
        List<ContactType> contactTypeList = otherFlowService.getContactTypeList(userLogin);

        return ResponseEntity.status(HttpStatus.OK).body(contactTypeList);
    }

    @PostMapping(ApiEndpoints.GET_CHANGED_LIST)
    public ResponseEntity<List<Object[]>> getChangedList(@RequestBody GetDelegatorsDTO delegatorsDTO) {
        List<Object[]> changedList = otherFlowService.getChangedList(delegatorsDTO);

        return ResponseEntity.status(HttpStatus.OK).body(changedList);
    }

    @PostMapping(ApiEndpoints.GET_PA_CLIENT_CONTACTS)
    public ResponseEntity<ClientContactsDTO> getPAClientContacts(@RequestParam(required = false) String nif,  @RequestParam(required = false) String selPlate,
                                                                 @RequestParam int idPaData, @RequestBody FilterBean oPAFilterBean) {
        ClientContactsDTO clientContacts = otherFlowService.getPAClientContacts(nif, selPlate, idPaData, oPAFilterBean);

        return ResponseEntity.status(HttpStatus.OK).body(clientContacts);
    }

    @PostMapping(ApiEndpoints.MAP_UPDATE)
    public ResponseEntity<String> mapUpdate(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestPart MultipartFile file) {
        String res = otherFlowService.mapUpdate(userPrincipal, file);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }
    @GetMapping(ApiEndpoints.GET_CLIENT_TYPE)
    public ResponseEntity<List<ClientType>> getClientType() {
        log.info("getClientType controller");
        List<ClientType> rs = otherFlowService.getClientTypes();
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @GetMapping(ApiEndpoints.GET_CHANNEL)
    public ResponseEntity<List<Channel>> getChannels() {
        log.info("getChannels controller");
        List<Channel> rs = otherFlowService.getChannels();
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }


    @GetMapping(ApiEndpoints.GET_SOURCE)
    public ResponseEntity<List<Source>> getSource() {
        log.info("getSource controller");
        List<Source> rs = otherFlowService.getSources();
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @GetMapping(ApiEndpoints.GET_ALL_CONTACT_TYPE)
    public ResponseEntity<List<ContactType>> getAllContactType() {
        log.info("getAllContactType controller");
        List<ContactType> rs = otherFlowService.getAllContactTypes();
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @GetMapping(ApiEndpoints.GET_MAIN_TYPE)
    public ResponseEntity<List<MaintenanceTypeDTO>> getMaintenanceTypes() {
        log.info("getAllContactType controller");
        List<MaintenanceTypeDTO> rs = otherFlowService.getMaintenanceTypes();
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @PostMapping(ApiEndpoints.VERIFY_IMAGE)
    public ResponseEntity<String> verifyImageNameOnServer(@RequestParam String fileName, @RequestParam String idTpaItemType,
                                                                            @RequestParam String tpaItemTypeNameSingular) {
        log.info("getAllContactType controller");
         otherFlowService.verifyImageNameOnServer(fileName, idTpaItemType, tpaItemTypeNameSingular);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

}
