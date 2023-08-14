package com.gsc.programaavisos.model.crm.entity;


import com.rg.dealer.Dealer;
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
@Table(name = "PA_DATA_INFO")
public class ProgramaAvisosBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "PA_ID_SOURCE")
    private int idSource;
    @Column(name = "PA_ID_DOCUMENT")
    private String idDocument;
    @Column(name = "PA_ID_CHANNEL")
    private int idChannel;
    @Column(name = "PA_ID_CONTACTTYPE")
    private int idContactType;
    @Column(name = "PA_ID_CLIENT_ORIGIN")
    private int idClientType;
    @Column(name = "PA_YEAR")
    private int year;
    @Column(name = "PA_MONTH")
    private int month;
    @Column(name = "PA_DAY")
    private int day;
    @Column(name = "PA_OID_DEALER")
    private String oidDealer;
    @Column(name = "PA_LICENSE_PLATE")
    private String licensePlate;
    @Column(name = "PA_BRAND")
    private String brand;
    @Column(name = "PA_MODEL")
    private String model;
    @Column(name = "PA_NIF")
    private String nif;
    @Column(name = "PA_NAME")
    private String name;
    @Column(name = "PA_ADDRESS")
    private String address;
    private String cp4;
    private String cp3;
    private String cpExt;
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
    private String blockedBy;
    private int nrMissedCalls;
    private String changedBy;
    private Timestamp dtChanged;
    private int nrCalls;
    private Integer idClaim;
    private int idClientChannelPreference;
    private String receiveInformation;
    private String oidNewsletter;
    private String newsletterPersonalData;
    private String oidDealerSchedule;
    private Date dtSchedule;
    private Time hrSchedule;
    private String owner;
    private String recoveryAndShipping;
    private String contactChannel;
    private String postalType;
    private String lastRevision;
    private String lastRevisionKm;
    private Date dtLastRevision;
    private String nextRevision;
    private int yearNextRevision;
    private int monthNextRevision;
    private Date dtNextRevision;
    private Date dtItv;
    private double maintenancePrice;
    private String eurocare;
    private String flag5Plus;
    private String flagSend;
    private String skinDoPostal;
    private String contactReason;
    private String flagMaintenanceContract;
    private boolean flagHybrid;
    private String acessoryCode1;
    private String acessory1;
    private String acessoryCode2;
    private String acessory2;
    private String serviceCode1;
    private String service1;
    private String serviceCode2;
    private String service2;
    private String serviceCode3;
    private String service3;
    private String cmkDmv1;
    private Date cmkDmv1DtEnd;
    private String cmkDmv1Image;
    private String cmkDav2;
    private Date cmkDav2DtEnd;
    private String cmkDav2Image;
    private String maintenancePlan;
    private String sendType;
    private double extraCarePlusCostPrice;
    private Date extraCarePlusDateLimit;
    private String description;
    private String expectedDate;
   // private Dealer dealer;
    //   private List<Campaign> technicalCampaigns;
    //  private List<Revision> revisions;
    //    private List<Warranty> warranties;
    //   private List<ECareNotification> eCareNotifications;
    //  private List<ECareNotification> eCareAllNotifications;
    private int IDOrigin;
    private int extIDInOrigin;
    //   private List<Claim> claims;
    //   private List<Rpt> rpts;
    private String extracare;
    private String dtNextIUC;
    private String dtStartNextITV;
    private String dtNextItv;
    // private MaintenanceContract maintenanceContract;
    private String tecnicalModel;
    private Integer indiceCSToyota;
    private String HHCCode;
    private String HHCDesig;
    private String HHCDisplayName;
    private String HHCDtStart;
    private String HHCDtEnd;
    private String HHCKmEnd;
    private Date dtFinishContract;
    private String techicalCampaignCode;
    private Date techicalCampaignSendDate1;
    private Date generationDate;
    private Date techicalCampaignSendDate2;
    private Date dtVisible;
    private int warningPriority;


}
