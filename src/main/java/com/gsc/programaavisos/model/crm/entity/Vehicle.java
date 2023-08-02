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
@Table(name = "vehicle_real")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "LICENCE_PLATE")
    private String licencePlate;

    private String vin;

    private String brand;
    private String model;
    private String version;
    private String color;
    @Column(name = "ID_OWNER")
    private Integer idOwner;

    @Column(name = "ID_USER")
    private Integer idUser;

    @Column(name = "ID_FINANCIAL")
    private Integer idFinancial;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;

    @Column(name = "CHANGED_BY")
    private String changedBy;
    @Column(name = "DT_CHANGED")
    private LocalDateTime dtChanged;

}
