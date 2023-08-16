package com.gsc.programaavisos.model.crm.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PA_CC_RIGOR_SERVICE")
public class CcRigorService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "OID_DEALER")
    private String oidDealer;

    @Column(name = "ID_CONTACTTYPE")
    private Integer idContactType;

    @Column(name = "YEAR_FROM")
    private Integer yearFrom;

    @Column(name = "MONTH_FROM")
    private Integer monthFrom;

    @Column(name = "YEAR_TO")
    private Integer yearTo;

    @Column(name = "MONTH_TO")
    private Integer monthTo;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "DT_CHANGED")
    private LocalDateTime dtChanged;
}
