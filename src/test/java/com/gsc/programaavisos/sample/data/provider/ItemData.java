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



    public static DocumentUnitDTO getDocumentUnitDTO(){
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
                .idTpaItem(RANDOM_ID+1)
                .serviceName(RANDOM_NAME)
                .category(RANDOM_ID)
                .description(RANDOM_NAME)
                .serviceLink(RANDOM_NAME)
                .endDateInput(RANDOM_LOCAL_DATE)
                .serviceCode(RANDOM_NAME)
                .build();
    }

    public static DocumentUnit getDocumentUnit(){
        return DocumentUnit.builder()
                .id(RANDOM_ID)
                .idBrand(ApiConstants.ID_BRAND_TOYOTA)
                .idDocumentUnitType(1)
                .idDocumentUnitCategory(2)
                .name("Nombre del Documento")
                .description("Descripción del Documento")
                .code("D001")
                .link("https://example.com/documento")
                .imgPostal("imagen_postal.jpg")
                .imgEPostal("imagen_electronic_postal.jpg")
                .status('A') // 'A' para activo, puedes usar otros estados según tu lógica
                .dtEnd(LocalDate.of(2023, 12, 31))
                .createdBy("UsuarioCreacion")
                .dtCreated(LocalDate.now())
                .changedBy("UsuarioModificacion")
                .dtChanged(LocalDate.now())
                .build();
    }

}
