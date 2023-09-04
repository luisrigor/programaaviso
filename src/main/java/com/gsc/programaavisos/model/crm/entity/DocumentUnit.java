package com.gsc.programaavisos.model.crm.entity;

import com.gsc.programaavisos.dto.DocumentUnitDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SqlResultSetMapping(
        name = "DocumentUnitMapping",
        classes = @ConstructorResult(
                targetClass = DocumentUnitDTO.class,
                columns = {
                        @ColumnResult(name = "ID", type = Integer.class),
                        @ColumnResult(name = "ID_BRAND", type = Integer.class),
                        @ColumnResult(name = "ID_DOCUMENT_UNIT_TYPE", type = Integer.class),
                        @ColumnResult(name = "ID_DOCUMENT_UNIT_CATEGORY", type = Integer.class),
                        @ColumnResult(name = "NAME", type = String.class),
                        @ColumnResult(name = "DESCRIPTION", type = String.class),
                        @ColumnResult(name = "CODE", type = String.class),
                        @ColumnResult(name = "LINK", type = String.class),
                        @ColumnResult(name = "IMG_POSTAL", type = String.class),
                        @ColumnResult(name = "IMG_E_POSTAL", type = String.class),
                        @ColumnResult(name = "STATUS", type = String.class),
                        @ColumnResult(name = "DT_END", type = LocalDate.class),
                        @ColumnResult(name = "CREATED_BY", type = String.class),
                        @ColumnResult(name = "DT_CREATED", type = LocalDate.class),
                        @ColumnResult(name = "CHANGED_BY", type = String.class),
                        @ColumnResult(name = "DT_CHANGED", type = LocalDate.class),
                        @ColumnResult(name = "CATEGORY_DESCRIPTION", type = String.class)
                }
        )
)
@Entity
@ToString
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
