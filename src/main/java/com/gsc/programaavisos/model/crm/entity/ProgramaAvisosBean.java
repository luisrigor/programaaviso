package com.gsc.programaavisos.model.crm.entity;


import com.gsc.ws.core.Campaign;
import com.gsc.ws.core.maintenancecontract.MaintenanceContract;
import lombok.*;
import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.gsc.ecare.core.ECareNotification;
import com.gsc.ws.core.*;
import com.rg.dealer.Dealer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PA_DATA_INFO")
@ToString
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
    @Column(name = "PA_OID_DEALER_SCHEDULE")
    private String oidDealerSchedule;
    @Column(name = "PA_VIN")
    private String vin;
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
    @Column(name = "PA_ID_CLIENT_CHANNEL_PREFERENCE")
    private Integer idClientChannelPreference;
    @Column(name = "PA_ID_CLIENT_ORIGIN")
    private Integer idClientOrigin;
    @Column(name = "PA_ID_STATUS")
    private Integer idStatus;
    @Column(name = "PA_RECEIVE_INFORMATION")
    private String receiveInformation;
    @Column(name = "PA_SUCCESS_CONTACT")
    private String successContact;
    @Column(name = "PA_NEW_ADDRESS")
    private String newAddress;
    @Column(name = "PA_NEW_ADDRESS_NUMBER")
    private String newAddressNumber;
    @Column(name = "PA_NEW_CONTACTPHONE")
    private String newContactPhone;
    @Column(name = "PA_NEW_CP3")
    private String newCp3;
    @Column(name = "PA_NEW_CP4")
    private String newCp4;
    @Column(name = "PA_NEW_CPEXT")
    private String newCpExt;
    @Column(name = "PA_NEW_EMAIL")
    private String newEmail;
    @Column(name = "PA_NEW_FLOOR")
    private String newFloor;
    @Column(name = "PA_NEW_NAME")
    private String newName;
    @Column(name = "PA_NEW_NIF")
    private String newNif;
    @Column(name = "PA_BLOCKED_BY")
    private String blockedBy;
    @Column(name = "PA_SUCCESS_MOTIVE")
    private String successMotive;
    @Column(name = "PA_DT_SCHEDULE_CONTACT")
    private Date dtScheduleContact;
    @Column(name = "PA_REVISION_SCHEDULE")
    private String revisionSchedule;
    @Column(name = "PA_REVISION_SCHEDULE_MOTIVE")
    private String revisionScheduleMotive;
    @Column(name = "PA_REVISION_SCHEDULE_MOTIVE2")
    private String revisionScheduleMotive2;
    @Column(name = "PA_RECOVERY_SHIPPING")
    private String recoveryAndShipping;
    @Column(name = "PA_OBSERVATIONS")
    private String observations;
    @Column(name = "PA_REMOVED_OBS")
    private String removedObs;
    @Column(name = "PA_DELEGATED_TO")
    private String delegatedTo;
    @Column(name = "PA_CLIENT")
    private String client;
    @Column(name = "PA_NR_MISSED_CALLS")
    private Integer nrMissedCalls;
    @Column(name = "PA_NR_CALLS")
    private Integer nrCalls;
    @Column(name = "PA_ID_LAST_CALL")
    private Integer idLastCall;
    @Column(name = "PA_VISIBLE")
    private String visible;
    @Column(name = "PA_DT_VISIBLE")
    private Date dtVisible;
    @Column(name = "PA_ID_CLAIM")
    private Integer idClaim;
    @Column(name = "PA_OID_NEWSLETTER")
    private String oidNewsletter;
    @Column(name = "PA_NEWSLETTER_PERSONAL_DATA")
    private String newsletterPersonalData;
    @Column(name = "PA_DT_SCHEDULE")
    private Date dtSchedule;
    @Column(name = "PA_ID_ORIGIN")
    private Integer idOrigin;
    @Column(name = "PA_EXT_ID_IN_ORIGIN")
    private Integer extIDInOrigin;
    @Column(name = "PA_CREATED_BY")
    private String createdBy;
    @Column(name = "PA_DT_CREATED")
    private String dtCreated;
    @Column(name = "PA_OWNER")
    private String owner;
    @Column(name = "PA_HR_SCHEDULE_CONTACT")
    private Time hrScheduleContact;
    @Column(name = "PA_CHANGED_BY")
    private String changedBy;
    @Column(name = "PA_DT_CHANGED")
    private Timestamp dtChanged;
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
    @Transient
    private String euroCare;
    @Column(name = "MRS_FLAG_5_PLUS")
    private Character flag5Plus;
    @Column(name = "MRS_FLAG_MAINTENANCE_CONTRACT")
    private Character flagMaintenanceContract;
    @Column(name = "MRS_FLAG_HYBRID")
    private Character flagHybrid;
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
    @Column(name = "EXTRACARE_PLUS_COST_PRICE")
    private Double extraCarePlusCostPrice;
    @Column(name = "EXTRACARE_PLUS_DT_LIMIT_RENOVATION")
    private Date extraCarePlusDateLimit;
    @Transient
    private String description;
    @Transient
    private String expectedDate;
    @Transient
    private Dealer dealer;
    @Transient
    private List<Campaign> technicalCampaigns;
    @Transient
    private List<Revision> revisions;
    @Transient
    private List<Warranty> warranties;
    @Transient
    private List<ECareNotification> eCareNotifications;
    @Transient
    private List<ECareNotification> eCareAllNotifications;
    @Transient
    private List<Claim> claims;
    @Transient
    private List<Rpt> rpts;
    @Transient
    private String extracare;
    @Transient
    private String dtNextIUC;
    @Transient
    private String dtStartNextITV;
    @Transient
    private String dtNextItv;
    @Transient
    private MaintenanceContract maintenanceContract;
    @Transient
    private String tecnicalModel;
    @Transient
    private Integer indiceCSToyota;

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
    @Column(name = "PA_WARNING_PRIORITY")
    private Integer warningPriority;
    @Column(name = "MC_DT_FINISH_CONTRACT")
    private Date dtFinishContract;
    @Column(name = "TC_CAMPAIGN")
    private String techicalCampaignCode;
    @Column(name = "TC_SEND_DATE")
    private Date techicalCampaignSendDate1;
    @Column(name = "TC_GENERATION_DATE")
    private Date generationDate;
    @Column(name = "TC_SEND_DATE_2")
    private Date tcSendDate2;
}
