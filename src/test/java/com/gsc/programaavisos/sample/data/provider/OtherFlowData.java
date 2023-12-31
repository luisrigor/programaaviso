package com.gsc.programaavisos.sample.data.provider;

import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.model.cardb.Fuel;
import com.gsc.programaavisos.model.cardb.entity.Modelo;
import com.gsc.programaavisos.model.crm.entity.*;
import com.rg.dealer.Dealer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class OtherFlowData {

    public static final Integer RANDOM_ID = 111;
    public static final String RANDOM_NAME = "RANDOM_NAME";
    public static final Integer RANDOM_MARCA_ID = 222;
    public static final String RANDOM_CAR_CONFIGURATOR_ID = "ID-12345678";
    public static final String RANDOM_DESIG = "AURIS RANDOM";
    public static final LocalDate RANDOM_DT_START = LocalDate.MIN;
    public static final LocalDate RANDOM_DT_END = LocalDate.MAX;
    public static final String RANDOM_CREATED_BY =  "USER RANDOM";

    /** Fuel Data*/
    public static final Integer RANDOM_FUEL_ID = 333;
    public static final String RANDOM_FUEL_DESCRIPTION = "DIESEL RANDOM";
    public static final Character RANDOM_STATUS = 'S';

    public static Modelo getModelo() {

        return Modelo.builder()
                .id(RANDOM_ID)
                .idMarca(RANDOM_MARCA_ID)
                .carConfiguratorId(RANDOM_CAR_CONFIGURATOR_ID)
                .desig(RANDOM_DESIG)
                .dtStart(RANDOM_DT_START)
                .dtEnd(RANDOM_DT_END)
                .createdBy(RANDOM_CREATED_BY)
                .build();
    }

    public static Fuel getFuel() {
        return Fuel.builder()
                .id(RANDOM_FUEL_ID)
                .description(RANDOM_FUEL_DESCRIPTION)
                .build();
    }

    public static ContactReason getContactReason() {
        return ContactReason.builder()
                .id(RANDOM_ID)
                .contactReason("GENERATOR")
                .build();
    }

    public static Genre getGenre() {
        return Genre.builder()
                .id(RANDOM_ID)
                .name("RANDOM_GENRE")
                .status(RANDOM_STATUS)
                .build();
    }

    public static Kilometers getKilometers(){
        return Kilometers.builder()
                .id(RANDOM_ID)
                .name(RANDOM_NAME)
                .build();
    }

    public static EntityType getEntity(){
        return EntityType.builder()
                .id(RANDOM_ID)
                .name(RANDOM_NAME)
                .status(RANDOM_STATUS)
                .build();
    }

    public static Age getAge(){
        return Age.builder()
                .id(RANDOM_ID)
                .name(RANDOM_NAME)
                .status(RANDOM_STATUS)
                .build();
    }

    public static Fidelitys getFidelity(){
        return Fidelitys.builder()
                .id(RANDOM_ID)
                .name(RANDOM_NAME)
                .status(RANDOM_STATUS)
                .build();
    }

    public static DocumentUnitDTO getDocumentUnit(){
        return DocumentUnitDTO.builder()
                .id(RANDOM_ID)
                .name(RANDOM_NAME)
                .categoryDescription("RANDOM CATEGORY")
                .description("RANDOM DESCRIPTION")
                .build();
    }

    public static DelegatorsDTO getDelegators(){
        DelegatorsValues delegatorsValues = new DelegatorsValues(RANDOM_NAME,RANDOM_NAME+" VALUE");
        return DelegatorsDTO.builder()
                .delegators(new ArrayList<>(Collections.singletonList(delegatorsValues)))
                .changedBy(new ArrayList<>(Collections.singletonList(delegatorsValues)))
                .build();
    }

    public static Dealer getDealer(){
        return new Dealer();
    }

    public static ContactType getContactType() {
        return ContactType.builder()
                .id(1)
                .name(RANDOM_NAME)
                .status(RANDOM_STATUS)
                .orderColumn(RANDOM_NAME)
                .orderColumnDescription(RANDOM_NAME+"D")
                .createdBy(RANDOM_CREATED_BY)
                .dtCreated(LocalDateTime.now())
                .build();
    }

    public static ClientPropDTO getClientPropDTO(){
        return ClientPropDTO.builder()
                .id(1)
                .nextRevision("2023-09-01")
                .licencePlate("ABC123")
                .build();
    }

    public static GetDelegatorsDTO getGetDelegatorsDTO(){
        return GetDelegatorsDTO.builder()
                .fromYear("2022")
                .toYear("2023")
                .fromMonth("01")
                .toMonth("12")
                .arrayOidDealer(new String[]{"dealer1", "dealer2"})
                .build();
    }

    public static ClientType getClientType(){
        return ClientType.builder()
                .id(1)
                .name("RANDOM_NAME")
                .status('S')
                .createdBy("RANDOM_CREATED_BY")
                .dtCreated(LocalDate.now())
                .build();
    }

    public static Channel getChannel(){
        return  Channel.builder()
                .id(1)
                .name(RANDOM_NAME)
                .status('S')
                .createdBy(RANDOM_CREATED_BY)
                .dtCreated(LocalDate.now())
                .build();
    }

    public static Source getSource(){
        return  Source.builder()
                .id(1)
                .name(RANDOM_NAME)
                .status('S')
                .createdBy(RANDOM_CREATED_BY)
                .dtCreated(LocalDate.now())
                .build();
    }


}
