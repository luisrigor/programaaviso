package com.gsc.programaavisos.controller;

import com.gsc.programaavisos.dto.PADTO;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.PAService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Log4j
@RequestMapping("${app.baseUrl}")
@Api(value = "", tags = "PA")
@RestController
@CrossOrigin("*")
public class PAController {

    private final PAService paService;

    @PostMapping("/savePa")
    public void savePA(@AuthenticationPrincipal UserPrincipal userPrincipal,
                       @RequestBody PADTO padto) {
        paService.savePA(userPrincipal,padto);
    }
}

