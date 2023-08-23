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
@Table(name = "PA_MRS_MAINTENANCETYPES")
public class MrsMaintenanceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "CREATE_BY")
    private String createBy;

    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;

}
