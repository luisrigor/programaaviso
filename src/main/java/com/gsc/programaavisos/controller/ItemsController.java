package com.gsc.programaavisos.controller;

import com.gsc.programaavisos.constants.ApiEndpoints;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ManageItemsDTO;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ItemService;
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
@Api(value = "", tags = "ITEMS")
@RestController
@CrossOrigin("*")
public class ItemsController {

    private final ItemService itemService;

    @GetMapping(ApiEndpoints.GET_SEARCH_ITEMS)
    public ResponseEntity<List<DocumentUnitDTO>> searchItems(@RequestParam(required = false) String searchInput,
                                                             @RequestParam(required = false) Date startDate,
                                                             @RequestParam(required = false) Integer tpaItemType,
                                                             @AuthenticationPrincipal UserPrincipal userPrincipal) {
        log.info("searchItems controller");
        List<DocumentUnitDTO> items = itemService.searchItems(searchInput,startDate,tpaItemType, userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }


    @GetMapping(ApiEndpoints.GET_MANAGE_ITEMS)
    public ResponseEntity<ManageItemsDTO> getManageItems(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                         @RequestParam int itemType, @RequestParam int itemId) {
        ManageItemsDTO manageItems = itemService.getManageItems(userPrincipal, itemType, itemId);

        return ResponseEntity.status(HttpStatus.OK).body(manageItems);
    }
}
