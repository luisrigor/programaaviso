package com.gsc.programaavisos.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentUnitDTO {


    private Integer id;
    private int idBrand;
    private int idDocumentUnitType;
    private int idDocumentUnitCategory;
    private String name;
    private String description;
    private String code;
    private String link;
    private String imgPostal;
    private String imgEPostal;
    private String status;
    private LocalDate dtEnd;
    private String createdBy;
    private LocalDate dtCreated;
    private String changedBy;
    private LocalDate dtChanged;
    private String categoryDescription;
}
