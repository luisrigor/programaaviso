package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PA_DATA")
public class ProgramaAvisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ID_SOURCE")
    private Integer idSource;
    @Column(name = "ID_DOCUMENT")
    private String idDocument;
    @Column(name = "ID_CHANNEL")
    private int idChannel;
    @Column(name = "ID_CONTACTTYPE")
    private int idContactType;
    @Column(name = "ID_CLIENT_TYPE")
    private int idClientType;
    @Column(name = "ID_CLIENT_ORIGIN")
    private int idClientOrigin;
    @Column(name = "ID_STATUS")
    private Integer idStatus;
    @Column(name = "YEAR")
    private int year;
    @Column(name = "MONTH")
    private int month;
    @Column(name = "DAY")
    private int day;
    @Column(name = "OID_DEALER")
    private String oidDealer;
    @Column(name = "LICENSE_PLATE")
    private String licensePlate;
    @Column(name = "VIN")
    private String vin;
    @Column(name = "BRAND")
    private String brand;
    @Column(name = "MODEL")
    private String model;
    @Column(name = "NIF")
    private String nif;
    private String name;
    private String address;
    private String cp4;
    private String cp3;
    private String cpext;
    @Column(name = "CONTACTPHONE")
    private String contactPhone;
    private String email;
    @Column(name = "DATA_IS_CORRECT")
    private String dataIsCorrect;
    @Column(name = "NEW_NIF")
    private String newNif;

    @Column(name = "NEW_NAME")
    private String newName;

    @Column(name = "NEW_ADDRESS")
    private String newAddress;

    @Column(name = "NEW_ADDRESS_NUMBER")
    private String newAddressNumber;

    @Column(name = "NEW_FLOOR")
    private String newFloor;

    @Column(name = "NEW_CP4")
    private String newCp4;

    @Column(name = "NEW_CP3")
    private String newCp3;

    @Column(name = "NEW_CPEXT")
    private String newCpExt;

    @Column(name = "NEW_CONTACTPHONE")
    private String newContactPhone;

    @Column(name = "NEW_EMAIL")
    private String newEmail;

    @Column(name = "SUCCESS_CONTACT")
    private String successContact;

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

    @Column(name = "OBSERVATIONS")
    private String observations;

    @Column(name = "REMOVED_OBS")
    private String removedObs;

    @Column(name = "DELEGATED_TO")
    private String delegatedTo;

    @Column(name = "CLIENT")
    private String client;
    @Column(name = "BLOCKED_BY")
    private String blockedBy;
    private String visible;
    @Column(name = "DT_VISIBLE")
    private Date dtVisible;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "DT_CREATED")
    private Timestamp dtCreated;
    @Column(name = "CHANGED_BY")
    private String changedBy;
    @Column(name = "DT_CHANGED")
    private Timestamp dtChanged;

    @Column(name = "NR_MISSED_CALLS")
    private int nrMissedCalls;

    @Column(name = "NR_CALLS")
    private int nrCalls;

    @Column(name = "ID_LAST_CALL")
    private int idLastCall;

    @Column(name = "ID_CLAIM")
    private Integer idClaim;

    @Column(name = "RECEIVE_INFORMATION")
    private String receiveInformation;

    @Column(name = "ID_CLIENT_CHANNEL_PREFERENCE")
    private Integer idClientChannelPreference;

    @Column(name = "OID_NEWSLETTER")
    private String oidNewsletter;

    @Column(name = "NEWSLETTER_PERSONAL_DATA")
    private String newsletterPersonalData;

    @Column(name = "OID_DEALER_SCHEDULE")
    private String oidDealerSchedule;

    @Column(name = "DT_SCHEDULE")
    private Date dtSchedule;

    @Column(name = "TIME_SCHEDULE")
    private Time hrSchedule;

    @Column(name = "OWNER")
    private String owner;

    @Column(name = "RECOVERY_SHIPPING")
    private String recoveryAndShipping;

    @Column(name = "ID_ORIGIN")
    private Integer idOrigin;

    @Column(name = "EXT_ID_IN_ORIGIN")
    private int extIDInOrigin;

    @Column(name = "WARNING_PRIORITY")
    private int warningPriority;

}
