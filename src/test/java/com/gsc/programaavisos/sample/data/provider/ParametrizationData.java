package com.gsc.programaavisos.sample.data.provider;

import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.dto.ParameterizationDTO;
import com.gsc.programaavisos.model.crm.entity.*;

import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class ParametrizationData {

    public static PaParameterization getPaParameterization(){
        return PaParameterization.builder()
                .id(OtherFlowData.RANDOM_ID)
                .dtStart(Date.valueOf(LocalDate.MIN))
                .dtEnd(Date.valueOf(LocalDate.MAX))
                .createdBy(OtherFlowData.RANDOM_CREATED_BY)
                .comments("comments")
                .idBrand(ApiConstants.ID_BRAND_TOYOTA)
                .build();
    }

    public static ParameterizationDTO getParameterizationDTO(){
        return ParameterizationDTO.builder()
                .id(1)
                .dtStart(new java.util.Date())
                .dtEnd(new java.util.Date())
                .comments("Comentario")
                .published('P')
                .visible('V')
                .type('T')
                .parametrizationItems(new ArrayList<>())
                .build();
    }

    public static ItemsAge getItemsAge() {
        return ItemsAge.builder()
                .id(OtherFlowData.RANDOM_ID)
                .idAge(OtherFlowData.RANDOM_ID + 1)
                .createdBy(OtherFlowData.RANDOM_CREATED_BY)
                .dtCreated(LocalDateTime.now())
                .build();
    }

        public static ItemsKilometers getItemsKilometers(){
            return ItemsKilometers.builder()
                    .id(OtherFlowData.RANDOM_ID)
                    .idKilometers(OtherFlowData.RANDOM_ID+1)
                    .createdBy(OtherFlowData.RANDOM_CREATED_BY)
                    .dtCreated(LocalDateTime.now())
                    .build();
    }

    public static ItemsFidelitys getItemFidelitys(){
        return ItemsFidelitys.builder()
                .id(OtherFlowData.RANDOM_ID)
                .idFidelity(OtherFlowData.RANDOM_ID+1)
                .createdBy(OtherFlowData.RANDOM_CREATED_BY)
                .dtCreated(LocalDateTime.now())
                .build();
    }

    public static ItemsFuel getItemFuels(){
        return ItemsFuel.builder()
                .id(OtherFlowData.RANDOM_ID)
                .idFuel(OtherFlowData.RANDOM_ID+1)
                .createdBy(OtherFlowData.RANDOM_CREATED_BY)
                .dtCreated(LocalDateTime.now())
                .build();
    }

    public static ItemsDealer getItemDealers(){
        return ItemsDealer.builder()
                .id(OtherFlowData.RANDOM_ID)
                .idDealer(String.valueOf(OtherFlowData.RANDOM_ID+1))
                .createdBy(OtherFlowData.RANDOM_CREATED_BY)
                .dtCreated(LocalDateTime.now())
                .build();
    }

    public static ItemsGenre getItemGenres(){
        return ItemsGenre.builder()
                .id(OtherFlowData.RANDOM_ID)
                .idGenre(OtherFlowData.RANDOM_ID+1)
                .createdBy(OtherFlowData.RANDOM_CREATED_BY)
                .dtCreated(LocalDateTime.now())
                .build();
    }

    public static ItemsModel getItemModels(){
        return ItemsModel.builder()
                .id(OtherFlowData.RANDOM_ID)
                .idGamma(OtherFlowData.RANDOM_ID+1)
                .createdBy(OtherFlowData.RANDOM_CREATED_BY)
                .dtCreated(LocalDateTime.now())
                .build();
    }

    public static ItemsEntityType getItemEntityTypes(){
        return ItemsEntityType.builder()
                .id(OtherFlowData.RANDOM_ID)
                .idEnittyType(OtherFlowData.RANDOM_ID+1)
                .createdBy(OtherFlowData.RANDOM_CREATED_BY)
                .dtCreated(LocalDateTime.now())
                .build();
    }

    public static ParametrizationItems getParametrizationItems(){
        return ParametrizationItems.builder()
                .id(OtherFlowData.RANDOM_ID)
                .idParameterization(getPaParameterization().getId())
                .itemAges(Collections.singletonList(ParametrizationData.getItemsAge()))
                .itemKilometers(Collections.singletonList(ParametrizationData.getItemsKilometers()))
                .itemFidelitys(Collections.singletonList(ParametrizationData.getItemFidelitys()))
                .itemFuels(Collections.singletonList(ParametrizationData.getItemFuels()))
                .itemDealers(Collections.singletonList(ParametrizationData.getItemDealers()))
                .itemGenres(Collections.singletonList(ParametrizationData.getItemGenres()))
                .itemModels(Collections.singletonList(ParametrizationData.getItemModels()))
                .itemEntityTypes(Collections.singletonList(ParametrizationData.getItemEntityTypes()))
                .build();
    }

}
