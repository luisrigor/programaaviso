package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "TECHNICAL_CAMPAIGNS")
public class TechnicalCampaigns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ID_PA_DATA")
    private Integer idPaData;
    @Column(name = "CAMPAIGN")
    private String campaign;
    @Column(name = "SEND_DATE")
    private LocalDate sendDate;
    @Column(name = "AGE")
    private Integer age;
    @Column(name = "GAMMA")
    private String gamma;
    @Column(name = "TECHNICAL_MODEL")
    private String technicalModel;
    @Column(name = "SALES_DATE")
    private String salesDate;
    @Column(name = "CONTACT_SOURCE")
    private String contactSource;
    @Column(name = "PHONE1")
    private String phone1;
    @Column(name = "PHONE2")
    private String phone2;
    @Column(name = "PHONE3")
    private String phone3;
    @Column(name = "EXLUDE")
    private String exlude;
    @Column(name = "GENERATION_DATE")
    private LocalDate generationDate;
    @Column(name = "SEND_DATE_2")
    private LocalDate sendDate2;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;
    @Column(name = "CHANGED_BY")
    private String changedBy;
    @Column(name = "DT_CHANGED")
    private LocalDateTime dtChanged;
    @Transient
    private StringBuilder stErrorImport;
}
