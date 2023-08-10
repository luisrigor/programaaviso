package com.gsc.programaavisos.sample.data.provider;

import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ManageItemsDTO;
import com.gsc.programaavisos.model.crm.entity.DocumentUnit;

import java.util.ArrayList;

public class ItemData {

    public static final Integer RANDOM_ID = 111;
    public static final String RANDOM_NAME = "RANDOM_NAME";


/*
    public static DocumentUnitDTO getDocumentUnit(){
        return DocumentUnitDTO.builder()
                .id(RANDOM_ID)
                .name(RANDOM_NAME)
                .categoryDescription("RANDOM CATEGORY")
                .description("RANDOM DESCRIPTION")
                .build();
    }
 */

    public static ManageItemsDTO getManageItemsDTO(){
        return ManageItemsDTO.builder()
                .item(new DocumentUnit())
                .categories(new ArrayList<>())
                .itemType(RANDOM_ID)
                .itemId(RANDOM_ID)
                .build();
    }

}
