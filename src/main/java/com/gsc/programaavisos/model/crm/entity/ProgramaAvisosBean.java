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
    @Column(name = "PA_ID_DOCUMENT")
    private String idDocument;
    @Column(name = "PA_ID_CHANNEL")
    private Integer idChannel;
    @Column(name = "PA_ID_CONTACTTYPE")
    private Integer idContactType;
    @Column(name = "PA_ID_CLIENT_TYPE")
    private Integer idClientType;
    @Column(name = "PA_YEAR")
    private Integer year;
    @Column(name = "PA_MONTH")
    private Integer month;
    @Column(name = "PA_DAY")
    private Integer day;
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
    @Column(name = "PA_CP4")
    private String cp4;
    @Column(name = "PA_CP3")
    private String cp3;
    @Column(name = "PA_CPEXT")
    private String cpExt;
    @Column(name = "PA_CONTACTPHONE")
    private String contactPhone;
    @Column(name = "PA_EMAIL")
    private String email;
    @Column(name = "PA_DATA_IS_CORRECT")
    private String dataIsCorrect;
    /*
    @Column(name = "PA_NEW_NIF")
    private String newNif;
    @Column(name = "PA_NEW_NAME")
    private String newName;
    @Column(name = "PA_NEW_ADDRESS")
    private String newAddress;
    @Column(name = "PA_NEW_ADDRESS_NUMBER")
    private String newAddressNumber;
    @Column(name = "PA_NEW_FLOOR")
    private String newFloor;
    @Column(name = "PA_NEW_CP4")
    private String newCp4;
    @Column(name = "PA_NEW_CP3")
    private String newCp3;
    @Column(name = "PA_NEW_CPEXT")
    private String newCpExt;
    @Column(name = "PA_NEW_CONTACTPHONE")
    private String newContactPhone;
    @Column(name = "PA_NEW_EMAIL")
    private String newEmail;
    @Column(name = "PA_SUCCESS_CONTACT")
    private String successContact;
    @Column(name = "PA_SUCCESS_MOTIVE")
    private String successMotive;
    @Column(name = "PA_DT_SCHEDULE_CONTACT")
    private Date dtScheduleContact;
    @Column(name = "PA_HR_SCHEDULE_CONTACT")
    private Time hrScheduleContact;
    @Column(name = "PA_REVISION_SCHEDULE")
    private String revisionSchedule;
    @Column(name = "PA_REVISION_SCHEDULE_MOTIVE")
    private String revisionScheduleMotive;
    @Column(name = "PA_REVISION_SCHEDULE_MOTIVE2")
    private String revisionScheduleMotive2;
    @Column(name = "PA_OBSERVATIONS")
    private String observations;
    @Column(name = "PA_REMOVED_OBS")
    private String removedObs;
    @Column(name = "PA_DELEGATED_TO")
    private String delegatedTo;
    @Column(name = "PA_CLIENT")
    private String client;
    @Column(name = "PA_BLOCKED_BY")
    private String blockedBy;
    @Column(name = "PA_NR_MISSED_CALLS")
    private Integer nrMissedCalls;
    @Column(name = "PA_CHANGED_BY")
    private String changedBy;
    @Column(name = "PA_DT_CHANGED")
    private Timestamp dtChanged;
    @Column(name = "PA_NR_CALLS")
    private Integer nrCalls;

    @Column(name = "PA_ID_CLAIM")
    private Integer idClaim;
    @Column(name = "PA_ID_CLIENT_CHANNEL_PREFERENCE")
    private Integer idClientChannelPreference;
    @Column(name = "PA_RECEIVE_INFORMATION")
    private String receiveInformation;
    @Column(name = "PA_OID_NEWSLETTER")
    private String oidNewsletter;
    @Column(name = "PA_NEWSLETTER_PERSONAL_DATA")
    private String newsletterPersonalData;
    @Column(name = "PA_OWNER")
    private String owner;
    @Column(name = "PA_RECOVERY_SHIPPING")
    private String recoveryAndShipping;
    @Column(name = "PA_ID_ORIGIN")
    private Integer IDOrigin;
    @Column(name = "PA_EXT_ID_IN_ORIGIN")
    private Integer extIDInOrigin;
    @Column(name = "MRS_NEXT_REVISION")
    private String nextRevision;
    @Column(name = "MRS_YEAR_NEXT_REVISION")
    private Integer yearNextRevision;
    @Column(name = "MRS_MONTH_NEXT_REVISION")
    private Integer monthNextRevision;
    @Column(name = "MRS_DT_NEXT_REVISION")
    private Date dtNextRevision;
    @Column(name = "MRS_DT_ITV")
    private Date dtItv;
    @Column(name = "MRS_MAINTENANCE_PRICE")
    private Double maintenancePrice;
    @Column(name = "MRS_FLAG_5_PLUS")
    private String flag5Plus;
    @Column(name = "MRS_FLAG_MAINTENANCE_CONTRACT")
    private String flagMaintenanceContract;
    @Column(name = "MRS_FLAG_HYBRID")
    private boolean flagHybrid;
    @Column(name = "MRS_ACESSORY_CODE_1")
    private String acessoryCodeOne;
    @Column(name = "MRS_ACESSORY_1")
    private String acessoryOne;
    @Column(name = "MRS_ACESSORY_CODE_2")
    private String acessoryCodeTwo;
    @Column(name = "MRS_ACESSORY_2")
    private String acessoryTwo;
    @Column(name = "MRS_SERVICE_CODE_1")
    private String serviceCodeOne;
    @Column(name = "MRS_SERVICE_1")
    private String serviceOne;
    @Column(name = "MRS_SERVICE_CODE_2")
    private String serviceCodeTwo;
    @Column(name = "MRS_SERVICE_2")
    private String serviceTwo;
    @Column(name = "MRS_SERVICE_CODE_3")
    private String serviceCodeThree;
    @Column(name = "MRS_SERVICE_3")
    private String serviceThree;
    @Column(name = "MRS_CMK_DMV_1")
    private String cmkDmv1;
    @Column(name = "MRS_CMK_DMV_1_DT_END")
    private Date cmkDmv1DtEnd;
    @Column(name = "MRS_CMK_DMV_1_IMAGE")
    private String cmkDmv1Image;
    @Column(name = "MRS_CMK_DAV_2")
    private String cmkDav2;
    @Column(name = "MRS_CMK_DAV_2_DT_END")
    private Date cmkDav2DtEnd;
    @Column(name = "MRS_CMK_DAV_2_IMAGE")
    private String cmkDav2Image;
    @Column(name = "MRS_MAINTENANCE_PLAN")
    private String maintenancePlan;
    @Column(name = "MRS_SEND_TYPE")
    private String sendType;
    //private double extraCarePlusCostPrice;
    // @Column(name = "EXTRACARE_PLUS_COST_PRICE")
    //private double extraCarePlusCostPrice;
    @Column(name = "EXTRACARE_PLUS_DT_LIMIT_RENOVATION")
    private Date extraCarePlusDateLimit;
    //private String description;
    //private String expectedDate;
    //private Dealer dealer;
    //private List<Campaign> technicalCampaigns;
    //private List<Revision> revisions;
    //private List<Warranty> warranties;
    //private List<ECareNotification> eCareNotifications;
    //private List<ECareNotification> eCareAllNotifications;
    //private Integer IDOrigin;
    //private Integer extIDInOrigin;
    //private List<Claim> claims;
    //private List<Rpt> rpts;
    //private String extracare;
    //private String dtNextIUC;
    //private String dtStartNextITV;
    //private String dtNextItv;
    //private MaintenanceContract maintenanceContract;
    //private String tecnicalModel;
    //private Integer indiceCSToyota;
    */

    @Column(name = "HHC_PRODUCT_ID")
    private String hHCProductId;
    @Column(name = "HHC_PRODUCT_DESCRIPTION")
    private String hHCProductDescription;
    @Column(name = "HHC_PRODUCT_DISPLAY_NAME")
    private String hHCDisplayName;
    @Column(name = "HHC_CONTRACT_START_DATE")
    private String hHCDtStart;
    @Column(name = "HHC_CONTRACT_END_DATE")
    private String hHCDtEnd;
    @Column(name = "HHC_CONTRACT_END_KM")
    private String hHCKmEnd;
    /*
    @Column(name = "MC_DT_FINISH_CONTRACT")
    private Date dtFinishContract;
    @Column(name = "TC_CAMPAIGN")
    private String techicalCampaignCode;
    @Column(name = "TC_SEND_DATE")
    private Date techicalCampaignSendDate1;
    @Column(name = "TC_GENERATION_DATE")
    private Date generationDate;
    @Column(name = "TC_SEND_DATE_2")
    private Date TC_SEND_DATE_2;
    @Column(name = "PA_DT_VISIBLE")
    private Date dtVisible;
    @Column(name = "PA_WARNING_PRIORITY")
    private int warningPriority;
    */

}
