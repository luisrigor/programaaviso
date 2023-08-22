package com.gsc.programaavisos.model.crm.entity;

import lombok.*;
import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PA_CALLS")
public class Calls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ID_PA_DATA")
    private Integer idPaData;
    @Column(name = "SUCCESS_CONTACT")
    private Character successContact;
    @Column(name = "SUCCESS_MOTIVE")
    private String successMotive;
    @Column(name = "DT_SCHEDULE_CONTACT")
    private Date dtScheduleContact;
    @Column(name = "HR_SCHEDULE_CONTACT")
    private Time hrScheduleContact;
    @Column(name = "REVISION_SCHEDULE")
    private String revisionSchedule;
    @Column(name = "REVISION_SCHEDULE_MOTIVE")
    private String revisionScheduleMotive;
    @Column(name = "REVISION_SCHEDULE_MOTIVE2")
    private String revisionScheduleMotive2;
    @Column(name = "REMOVED_OBS")
    private String removedObs;
    @Column(name = "IS_CONTACT_CC_RIGOR")
    private boolean isContactCallCenterRigor;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "DT_CREATED")
    private LocalDate dtCreated;
    @Column(name = "RECOVERY_SHIPPING")
    private String recoveryAndShipping;
}
