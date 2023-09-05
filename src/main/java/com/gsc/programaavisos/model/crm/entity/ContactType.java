package com.gsc.programaavisos.model.crm.entity;

import com.gsc.programaavisos.dto.MaintenanceTypeDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PA_CONTACTTYPE")
@SqlResultSetMapping(
        name = "MaintenanceTypeMapping",
        classes = @ConstructorResult(
                targetClass = MaintenanceTypeDTO.class,
                columns = {
                        @ColumnResult(name = "ID", type = Integer.class),
                        @ColumnResult(name = "CONTRACT_TYPE", type = String.class),
                        @ColumnResult(name = "MAINTENANCE_TYPE", type = String.class),
                }
        )
)
public class ContactType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private Character status;

    @Column(name = "ORDER_COLUMN")
    private String orderColumn;

    @Column(name = "ORDER_COLUMN_DESCRIPTION")
    private String orderColumnDescription;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;
}
