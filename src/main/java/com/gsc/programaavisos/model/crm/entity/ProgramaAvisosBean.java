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
@SqlResultSetMapping(
        name = "GetPATotalsMapping",
        classes = {
                @ConstructorResult(
                        targetClass = PATotals.class,
                        columns = {
                                @ColumnResult(name = "TOTAL", type = Integer.class),
                                @ColumnResult(name = "DONE", type = Integer.class),
                                @ColumnResult(name = "NOT_DONE", type = Integer.class),
                                @ColumnResult(name = "WITH_APPOINTMENT", type = Integer.class),
                                @ColumnResult(name = "SCHEDULE", type = Integer.class),
                                @ColumnResult(name = "REMOVED_MANUALLY", type = Integer.class),
                                @ColumnResult(name = "REMOVED_AUTO_BY_MANUT", type = Integer.class),
                                @ColumnResult(name = "REMOVED_AUTO_BY_PERIOD", type = Integer.class),
                        }
                )
        }
)
public class ProgramaAvisosBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PA_ID")
    private Integer id;
    @Column(name = "PA_ID_SOURCE")
    private Integer idSource;
    private String idDocument;
    private Integer idChannel;
    private Integer idContactType;
    private Integer idClientType;
    private Integer year;
    private Integer month;
    private Integer day;
    private String oidDealer;
    private String licensePlate;
    private String brand;
    private String model;
    private String nif;
    private String name;
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
    private Integer nrMissedCalls;
    private String changedBy;
    private Timestamp dtChanged;
    private Integer nrCalls;
    private Integer idClaim;
    private Integer idClientChannelPreference;
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
    private Integer yearNextRevision;
    private Integer monthNextRevision;
    private Date dtNextRevision;
    private Date dtItv;
    private Double maintenancePrice;
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
    //private Dealer dealer;
    //private List<Campaign> technicalCampaigns;
   //private List<Revision> revisions;
   //private List<Warranty> warranties;
   //private List<ECareNotification> eCareNotifications;
   //private List<ECareNotification> eCareAllNotifications;
    private Integer IDOrigin;
    private Integer extIDInOrigin;
    //private List<Claim> claims;
   //private List<Rpt> rpts;
    private String extracare;
    private String dtNextIUC;
    private String dtStartNextITV;
    private String dtNextItv;
    //private MaintenanceContract maintenanceContract;
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
