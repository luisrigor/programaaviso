package com.gsc.programaavisos.sample.data.provider;

import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ItemFilter;
import com.gsc.programaavisos.dto.ManageItemsDTO;
import com.gsc.programaavisos.dto.SaveManageItemDTO;
import com.gsc.programaavisos.model.crm.entity.DocumentUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class ItemData {

    public static final Integer RANDOM_ID = 111;
    public static final String RANDOM_NAME = "RANDOM_NAME";
    public static final Date RANDOM_DATE = new Date(111111L);
    public static final LocalDate RANDOM_LOCAL_DATE = LocalDate.now();



    public static DocumentUnitDTO getDocumentUnit(){
        return DocumentUnitDTO.builder()
                .id(RANDOM_ID)
                .name(RANDOM_NAME)
                .categoryDescription("RANDOM CATEGORY")
                .description("RANDOM DESCRIPTION")
                .build();
    }


    public static ManageItemsDTO getManageItemsDTO(){
        return ManageItemsDTO.builder()
                .item(new DocumentUnit())
                .categories(new ArrayList<>())
                .itemType(RANDOM_ID)
                .itemId(RANDOM_ID)
                .build();
    }

    public static ItemFilter getItemFilter(){
        return ItemFilter.builder()
                .itemType(RANDOM_ID)
                .idBrand(RANDOM_ID+1)
                .searchInput(RANDOM_NAME)
                .dtEnd(RANDOM_DATE)
                .build();
    }

    public static SaveManageItemDTO getSaveManageItemDto(){
        return SaveManageItemDTO.builder()
                .idItemType(RANDOM_ID)
                .serviceName(RANDOM_NAME)
                .category(RANDOM_ID)
                .description(RANDOM_NAME)
                .serviceLink(RANDOM_NAME)
                .endDateInput(RANDOM_LOCAL_DATE)
                .serviceCode(RANDOM_NAME)
                .build();
    }

}
