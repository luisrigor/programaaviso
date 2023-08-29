package com.gsc.programaavisos.model.crm.entity;

import com.sc.commons.exceptions.SCErrorException;
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
    private int idStatus;
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
    @Column(name = "NAME")
    private String name;
    private String address;
    private String cp4;
    private String cp3;
    private String cpext;
    private String contactPhone;
    private String email;
    private String dataIsCorrect;
    private String newNif;
    private String newName;
    private String newAddress;
    private String newAddressNumber;
    private String newFloor;
    private String newCp4;
    private String newCp3;
    private String newCpExt;
    private String newContactPhone;
    private String newEmail;
    private String successContact;
    private String successMotive;
    private Date dtScheduleContact;
    private Time hrScheduleContact;
    private String revisionSchedule;
    private String revisionScheduleMotive;
    private String revisionScheduleMotive2;
    private String observations;
    private String removedObs;
    private String delegatedTo;
    private String client;
    @Column(name = "BLOCKED_BY")
    private String blockedBy;
    private String visible;
    private Date dtVisible;
    private String createdBy;
    private Timestamp dtCreated;
    private String changedBy;
    private Timestamp dtChanged;
    private int nrMissedCalls;
    private int nrCalls;
    private int idLastCall;
    private Integer idClaim;
    private String receiveInformation;
    private int idClientChannelPreference;
    private String oidNewsletter;
    private String newsletterPersonalData;
    private String oidDealerSchedule;
    private Date dtSchedule;
    private Time hrSchedule;
    private String owner;
    private String recoveryAndShipping;
    private int idOrigin;
    private int extIDInOrigin;
    private int warningPriority;


    @Transient
    private Mrs MRS;
/*

    public Mrs getMRS() throws SCErrorException {
        if (ivMRS == null && (getIdContactType() == ContactType.MAN || getIdContactType() == ContactType.MAN_ITV || getIdContactType() == ContactType.ITV))
            ivMRS = Mrs.getHelper().getByIdPaData(getId());

        return ivMRS;
    }

 */


}
