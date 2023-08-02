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
@Table(name = "MODELO")
public class Modelo {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ID_MARCA")
    private Integer idMarca;

    @Column(name = "CAR_CONFIGURATORID", length = 100)
    private String carConfiguratorId;

    @Column(name = "DESIG")
    private String desig;

    @Column(name = "DT_START")
    private LocalDate dtStart;

    @Column(name = "DT_END")
    private LocalDate dtEnd;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DT_CREATED")
    private LocalDate dtCreated;

    @Column(name = "CHANGED_BY", length = 100)
    private String changedBy;

    @Column(name = "DT_CHANGED")
    private LocalDate dtChanged;

    @Column(name = "ACCOUNTING_ORDER", length = 15)
    private String accountingOrder;

    @Column(name = "ACCOUNTING_ORDER_NET", length = 15)
    private String accountingOrderNet;

    @Column(name = "LINK_ACCESSORIES_CATALOG", length = 200)
    private String linkAccessoriesCatalog;

    @Column(name = "MAX_EXPENSES")
    private Double maxExpenses;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "VEHICLE_GOES_TO_WAREHOUSE")
    private Character vehicleGoesToWarehouse;

    @Column(name = "PUBLISH_IN_SITE")
    private Character publishInSite;

    @Column(name = "PUBLISH_IN_CONFIGURATOR")
    private Character publishInConfigurator;

    @Column(name = "EXT_IMS_DESCRIPTION", length = 15)
    private String extImsDescription;

    @Column(name = "DESIG_CONFIGURADOR_FABRICA", length = 50)
    private String desigConfiguradorFabrica;

    @Column(name = "ID_CATEGORIA_FISCAL")
    private Integer idCategoriaFiscal;
}
