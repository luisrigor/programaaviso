package com.gsc.programaavisos.model.cardb.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "COMBUSTIVEL")
public class Combustivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "COD_INTERNACIONAL")
    private String codInternacional;

    @Column(name = "COD_LOCAL")
    private String codLocal;

    @Column(name = "DESIG")
    private String desig;

    @Column(name = "DT_START")
    private LocalDate dtStart;

    @Column(name = "DT_END")
    private LocalDate dtEnd;

    @Column(name = "PRIORIDADE")
    private Integer prioridade;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DT_CREATED")
    private LocalDate dtCreated;

    @Column(name = "CAR_CONFIGURATORID")
    private String carConfiguratorId;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "DT_CHANGED")
    private LocalDate dtChanged;

    @Column(name = "BRAND")
    private String brand;

    @Column(name = "A2P_ID")
    private String a2pId;

    @Column(name = "ID_BRANDS")
    private String idBrands;

    @Column(name = "EXT_ID_IMS")
    private String extIdIms;

    @Column(name = "DESIG_EN")
    private String desigEn;

    @Column(name = "DESIG_FR")
    private String desigFr;
}
