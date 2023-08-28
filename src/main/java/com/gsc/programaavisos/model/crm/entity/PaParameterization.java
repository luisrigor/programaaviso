package com.gsc.programaavisos.model.crm.entity;


import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PA_PARAMETERIZATION")
@ToString
public class PaParameterization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "DT_START")
    private Date dtStart;

    @Column(name = "DT_END")
    private Date dtEnd;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "PUBLISHED")
    private Character published;

    @Column(name = "VISIBLE")
    private Character visible;

    @Column(name = "TYPE")
    private Character type;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "DT_CHANGED")
    private LocalDateTime dtChanged;

    @Column(name = "ID_BRAND")
    private Integer idBrand;

    @Transient
    private List<ParameterizationItems> parameterizationItems;
}
