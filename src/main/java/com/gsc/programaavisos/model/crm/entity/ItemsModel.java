package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PA_PARAMETERIZATION_ITEMS_GAMMA")
public class ItemsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ID_PARAMETERIZATION_ITEMS")
    private Integer idParameterizationItems;
    @Column(name = "ID_GAMMA")
    private Integer idGamma;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;
}
