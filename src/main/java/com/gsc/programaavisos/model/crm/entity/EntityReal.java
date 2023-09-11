package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "entity_real")
public class EntityReal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ENTITY_TYPE")
    private String entityType;
    private String name;
    @Column(name = "BIRTH_YEAR")
    private Integer birthYear;
    @Column(name = "BIRTH_MONTH")
    private Integer birthMonth;
    @Column(name = "BIRTH_DAY")
    private Integer birthDay;
    private Character gender;
    @Column(name = "CIVIL_STATUS")
    private String civilStatus;
    private String nif;
    private String cc1;
    private Character cc2;
    private Character cc3;
    private Character cc4;
    private String address;
    private String cpext;
    private String municipality;
    private String district;
    private String email;
    private String phone1;
    private String phone2;
    private String fax;
    private String observations;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;
    @Column(name = "CHANGED_BY")
    private String changedBy;
    @Column(name = "DT_CHANGED")
    private LocalDateTime dtChanged;
    @Column(name = "HOUSE_NUMBER")
    private String houseNumber;
    private String floor;
    private String phone3;
    private String cp4;
    private String cp3;
    @Column(name = "OID_PREFERED_DEALER")
    private String oidPreferedDealer;
    private String household;
    @Column(name = "MYTOYOTA_EMAIL")
    private String myToyotaEmail;
}
