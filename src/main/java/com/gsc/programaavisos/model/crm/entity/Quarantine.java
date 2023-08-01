package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "QUARANTINE")
public class Quarantine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ENTITY_TYPE")
    private String entityType;
    @Column(name = "NAME")
    private String name;
    @Column(name = "BIRTH_YEAR")
    private String birthYear;
    @Column(name = "BIRTH_MONTH")
    private String birthMonth;
    @Column(name = "BIRTH_DAY")
    private String birthDay;
    @Column(name = "GENDER")
    private String gender;

    @Column(name = "PHONE1")
    private String phone1;
    @Column(name = "CIVIL_STATUS")
    private String civilStatus;
    @Column(name = "NIF")
    private String nif;
    @Column(name = "CC1")
    private String cc1;
    @Column(name = "CC2")
    private String cc2;
    @Column(name = "CC3")
    private String CC3;
    @Column(name = "CC4")
    private String cc4;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "CPEXT")
    private String cpExt;
    @Column(name = "MUNICIPALITY")
    private String municipaly;
    @Column(name = "ID_ORIGIN")
    private Integer idOrigin;
    @Column(name = "ID_EVENT")
    private Integer idEvent;
    @Column(name = "EVENT_DATE")
    private Date eventDate;
    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;
    @Column(name = "FLOOR")
    private String floor;
    @Column(name = "CP4")
    private String cp4;
    @Column(name = "CP3")
    private String cp3;
    @Column(name = "OID_DEALER_PARENT")
    private String dealerParent;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "LICENCE_PLATE")
    private String licencePlate;
    @Column(name = "VIN")
    private String vin;
    @Column(name = "IS_VEHICLE_OWNER")
    private String isVehicleOwner;
    @Column(name = "PA_PREFERED_COMMUNICATION_CHANNEL")
    private String paPreferredCommunicationnChannel;

    @Column(name = "CREATED_BY")
    private String createdBy;



}
