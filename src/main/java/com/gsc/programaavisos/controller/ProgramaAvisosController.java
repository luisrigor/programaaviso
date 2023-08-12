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

    @PutMapping(ApiEndpoints.UNLOCK_PA)
    public void unlockPARegister(@RequestParam Integer id) {
        log.info("unlockPARegister controller");
        programaAvisosService.unlockPARegister(id);
    }
}
