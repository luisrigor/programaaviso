package com.gsc.programaavisos.controller;


import com.google.gson.Gson;
import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ProgramaAvisosService;
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
@Api(value = "", tags = "AVISOS")
@RestController
@CrossOrigin("*")
public class ProgramaAvisosController {

    private final ProgramaAvisosService programaAvisosService;

    @PostMapping(ApiEndpoints.SEARCH_PA)
    public ResponseEntity<?> searchPA(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                               @RequestBody SearchPADTO searchPADTO) {
        log.info("searchPA controller");
        FilterBean filterBean = programaAvisosService.searchPA(userPrincipal, searchPADTO);
        Gson gson = new Gson();

        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(filterBean));
    }

    @PostMapping(ApiEndpoints.SAVE_PA)
    public ResponseEntity<String> savePA(@AuthenticationPrincipal UserPrincipal userPrincipal,
                       @RequestBody PADTO padto) {
        log.info("savePA controller");
        programaAvisosService.savePA(userPrincipal,padto);
        return ResponseEntity.status(HttpStatus.OK).body("saved");
    }

    @PostMapping(ApiEndpoints.REMOVE_PA)
    public ResponseEntity<String> removePA(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                           @RequestParam Integer id,
                                           @RequestParam String removedOption,
                                           @RequestParam String removedObs) {
        log.info("removePA controller");
        programaAvisosService.removePA(userPrincipal,id,removedOption,removedObs);
        return ResponseEntity.status(HttpStatus.OK).body("removed");
    }
    @PutMapping(ApiEndpoints.UNLOCK_PA)
    public ResponseEntity<String> unlockPARegister(@RequestParam Integer id) {
        log.info("unlockPARegister controller");
        programaAvisosService.unlockPARegister(id);
        return ResponseEntity.status(HttpStatus.OK).body("unlocked");
    }

    @GetMapping(ApiEndpoints.LIST_PA)
    public ResponseEntity<?> paInfoList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Gson gson = new Gson();
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(programaAvisosService.getInfoPA(userPrincipal)));

    }

    @PutMapping(ApiEndpoints.ACTIVE_PA)
    public ResponseEntity<String> activePA(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                           @RequestParam Integer id) {
        log.info("activePA controller");
        programaAvisosService.activatePA(userPrincipal,id);
        return ResponseEntity.status(HttpStatus.OK).body("activePA");
    }

    @GetMapping(ApiEndpoints.GET_PA_DETAIL)
    public ResponseEntity<DetailsPADTO> getPaDetail(@AuthenticationPrincipal UserPrincipal userPrincipal,
                            @RequestParam Integer id,
                            @RequestParam Integer oldId) {
        DetailsPADTO detailsInfo = programaAvisosService.getPaDetail(userPrincipal,id,oldId);
        return ResponseEntity.status(HttpStatus.OK).body(detailsInfo);
    }

    @GetMapping(ApiEndpoints.GET_TPA_SIMULATOR)
    public ResponseEntity<TpaSimulation> getTPASimulator(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                     @RequestBody TpaDTO tpaDTO) {
        TpaSimulation tpaSimulation = programaAvisosService.getTpaSimulation(userPrincipal, tpaDTO);
        return ResponseEntity.status(HttpStatus.OK).body(tpaSimulation);
    }
    @PostMapping(ApiEndpoints.UPLOAD_FILE)
    public ResponseEntity<String> uploadFile(@AuthenticationPrincipal UserPrincipal userPrincipal,  @RequestPart MultipartFile file) {
        log.info("uploadFile controller");
        programaAvisosService.uploadFile(userPrincipal, file);

        return ResponseEntity.status(HttpStatus.OK).body("Document successfully imported.");
    }
}
