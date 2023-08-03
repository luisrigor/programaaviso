package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PA_DOCUMENT_UNIT")
public class DocumentUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ID_BRAND")
    private int idBrand;
    @Column(name = "ID_DOCUMENT_UNIT_TYPE")
    private int idDocumentUnitType;
    @Column(name = "ID_DOCUMENT_UNIT_CATEGORY")
    private int idDocumentUnitCategory;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "CODE")
    private String code;
    @Column(name = "LINK")
    private String link;
    @Column(name = "IMG_POSTAL")
    private String imgPostal;
    @Column(name = "IMG_E_POSTAL")
    private String imgEPostal;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "DT_END")
    private LocalDate dtEnd;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "DT_CREATED")
    private LocalDate dtCreated;
    @Column(name = "CHANGED_BY")
    private String changedBy;
    @Column(name = "DT_CHANGED")
    private LocalDate dtChanged;
}
