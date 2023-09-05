package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PA_DATA_INFO")
public class PaDataInfo {
    @Id
    @Column(name = "PA_ID")
    private Integer paId;

    @Column(name = "PA_ID_SOURCE")
    private Integer paIdSource;

    @Column(name = "PA_ID_DOCUMENT")
    private String paIdDocument;

    @Column(name = "PA_ID_CHANNEL")
    private Integer paIdChannel;

    @Column(name = "PA_ID_CONTACTTYPE")
    private Integer paIdContactType;

    @Column(name = "PA_ID_CLIENT_ORIGIN")
    private Integer paIdClientOrigin;

    @Column(name = "PA_ID_STATUS")
    private Integer paIdStatus;

    @Column(name = "PA_YEAR")
    private Integer paYear;

    @Column(name = "PA_MONTH")
    private Integer paMonth;

    @Column(name = "PA_DAY")
    private Integer paDay;

    @Column(name = "PA_OID_DEALER")
    private String paOidDealer;

    @Column(name = "PA_LICENSE_PLATE")
    private String paLicensePlate;

    @Column(name = "PA_VIN")
    private String paVin;

    @Column(name = "PA_BRAND")
    private String paBrand;

    @Column(name = "PA_MODEL")
    private String paModel;

    @Column(name = "PA_NIF")
    private String paNif;

    @Column(name = "PA_NAME")
    private String paName;

    @Column(name = "PA_ADDRESS")
    private String paAddress;

    @Column(name = "PA_CP4")
    private String paCp4;

    @Column(name = "PA_CP3")
    private String paCp3;

    @Column(name = "PA_CPEXT")
    private String paCpExt;

    @Column(name = "PA_CONTACTPHONE")
    private String paContactPhone;

    @Column(name = "PA_EMAIL")
    private String paEmail;

    @Column(name = "PA_DATA_IS_CORRECT")
    private Character paDataIsCorrect;

    @Column(name = "PA_NEW_NIF")
    private String paNewNif;

    @Column(name = "PA_NEW_NAME")
    private String paNewName;

    @Column(name = "PA_NEW_ADDRESS")
    private String paNewAddress;

    @Column(name = "PA_NEW_ADDRESS_NUMBER")
    private String paNewAddressNumber;

    @Column(name = "PA_NEW_FLOOR")
    private String paNewFloor;

    @Column(name = "PA_NEW_CP4")
    private String paNewCp4;

    @Column(name = "PA_NEW_CP3")
    private String paNewCp3;

    @Column(name = "PA_NEW_CPEXT")
    private String paNewCpExt;

    @Column(name = "PA_NEW_CONTACTPHONE")
    private String paNewContactPhone;

    @Column(name = "PA_NEW_EMAIL")
    private String paNewEmail;

    @Column(name = "PA_SUCCESS_CONTACT")
    private Character paSuccessContact;

    @Column(name = "PA_SUCCESS_MOTIVE")
    private String paSuccessMotive;

    @Column(name = "PA_DT_SCHEDULE_CONTACT")
    private LocalDate paDtScheduleContact;

    @Column(name = "PA_HR_SCHEDULE_CONTACT")
    private LocalTime paHrScheduleContact;

    @Column(name = "PA_REVISION_SCHEDULE")
    private String paRevisionSchedule;

    @Column(name = "PA_REVISION_SCHEDULE_MOTIVE")
    private String paRevisionScheduleMotive;

    @Column(name = "PA_REVISION_SCHEDULE_MOTIVE2")
    private String paRevisionScheduleMotive2;

    @Column(name = "PA_RECOVERY_SHIPPING")
    private String paRecoveryShipping;

    @Column(name = "PA_OBSERVATIONS")
    private String paObservations;

    @Column(name = "PA_REMOVED_OBS")
    private String paRemovedObs;

    @Column(name = "PA_DELEGATED_TO")
    private String paDelegatedTo;

    @Column(name = "PA_CLIENT")
    private String paClient;

    @Column(name = "PA_BLOCKED_BY")
    private String paBlockedBy;

    @Column(name = "PA_NR_MISSED_CALLS")
    private Integer paNrMissedCalls;

    @Column(name = "PA_NR_CALLS")
    private Integer paNrCalls;

    @Column(name = "PA_ID_LAST_CALL")
    private Integer paIdLastCall;

    @Column(name = "PA_VISIBLE")
    private Character paVisible;

    @Column(name = "PA_DT_VISIBLE")
    private LocalDate paDtVisible;

    @Column(name = "PA_ID_CLIENT_TYPE")
    private Integer paIdClientType;

    @Column(name = "PA_ID_CLAIM")
    private Integer paIdClaim;

    @Column(name = "PA_RECEIVE_INFORMATION")
    private String paReceiveInformation;

    @Column(name = "PA_ID_CLIENT_CHANNEL_PREFERENCE")
    private Integer paIdClientChannelPreference;

    @Column(name = "PA_OID_NEWSLETTER")
    private String paOidNewsletter;

    @Column(name = "PA_NEWSLETTER_PERSONAL_DATA")
    private String paNewsletterPersonalData;

    @Column(name = "PA_DT_SCHEDULE")
    private LocalDate paDtSchedule;

    @Column(name = "PA_HR_SCHEDULE")
    private LocalTime paHrSchedule;

    @Column(name = "PA_OID_DEALER_SCHEDULE")
    private String paOidDealerSchedule;

    @Column(name = "PA_ID_ORIGIN")
    private Integer paIdOrigin;

    @Column(name = "PA_EXT_ID_IN_ORIGIN")
    private Integer paExtIdInOrigin;

    @Column(name = "PA_CREATED_BY")
    private String paCreatedBy;

    @Column(name = "PA_DT_CREATED")
    private LocalDateTime paDtCreated;

    @Column(name = "PA_CHANGED_BY")
    private String paChangedBy;

    @Column(name = "PA_DT_CHANGED")
    private LocalDateTime paDtChanged;

    @Column(name = "PA_OWNER")
    private String paOwner;

    @Column(name = "PA_WARNING_PRIORITY")
    private Integer paWarningPriority;

    @Column(name = "MRS_ID")
    private Integer mrsId;

    @Column(name = "MRS_DEALER_CODE")
    private String mrsDealerCode;

    @Column(name = "MRS_AFTERSALES_CODE")
    private String mrsAftersalesCode;

    @Column(name = "MRS_NEXT_REVISION")
    private String mrsNextRevision;

    @Column(name = "MRS_YEAR_NEXT_REVISION")
    private Integer mrsYearNextRevision;

    @Column(name = "MRS_MONTH_NEXT_REVISION")
    private Integer mrsMonthNextRevision;

    @Column(name = "MRS_DT_NEXT_REVISION")
    private LocalDate mrsDtNextRevision;

    @Column(name = "MRS_DT_ITV")
    private LocalDate mrsDtItv;

    @Column(name = "MRS_MAINTENANCE_PRICE")
    private Double mrsMaintenancePrice;

    @Column(name = "MRS_FLAG_5_PLUS")
    private Character mrsFlag5Plus;

    @Column(name = "MRS_FLAG_MAINTENANCE_CONTRACT")
    private Character mrsFlagMaintenanceContract;

    @Column(name = "MRS_FLAG_HYBRID")
    private Character mrsFlagHybrid;

    @Column(name = "MRS_ACESSORY_CODE_1")
    private String mrsAccessoryCode1;

    @Column(name = "MRS_ACESSORY_1")
    private String mrsAccessory1;

    @Column(name = "MRS_ACESSORY_CODE_2")
    private String mrsAccessoryCode2;

    @Column(name = "MRS_ACESSORY_2")
    private String mrsAccessory2;

    @Column(name = "MRS_SERVICE_CODE_1")
    private String mrsServiceCode1;

    @Column(name = "MRS_SERVICE_1")
    private String mrsService1;

    @Column(name = "MRS_SERVICE_CODE_2")
    private String mrsServiceCode2;

    @Column(name = "MRS_SERVICE_2")
    private String mrsService2;

    @Column(name = "MRS_SERVICE_CODE_3")
    private String mrsServiceCode3;

    @Column(name = "MRS_SERVICE_3")
    private String mrsService3;

    @Column(name = "MRS_CMK_DMV_1")
    private String mrsCmkDmv1;

    @Column(name = "MRS_CMK_DMV_1_DT_END")
    private LocalDate mrsCmkDmv1DtEnd;

    @Column(name = "MRS_CMK_DMV_1_IMAGE")
    private String mrsCmkDmv1Image;

    @Column(name = "MRS_CMK_DAV_2")
    private String mrsCmkDav2;

    @Column(name = "MRS_CMK_DAV_2_DT_END")
    private LocalDate mrsCmkDav2DtEnd;

    @Column(name = "MRS_CMK_DAV_2_IMAGE")
    private String mrsCmkDav2Image;

    @Column(name = "MRS_MAINTENANCE_PLAN")
    private String mrsMaintenancePlan;

    @Column(name = "MRS_SEND_TYPE")
    private Character mrsSendType;

    @Column(name = "MRS_SENT_INFO")
    private String mrsSentInfo;

    @Column(name = "MRS_DT_LAST_REVISION")
    private LocalDate mrsDtLastRevision;

    @Column(name = "EXTRACARE_PLUS_COST_PRICE")
    private Double extracarePlusCostPrice;

    @Column(name = "EXTRACARE_PLUS_DT_LIMIT_RENOVATION")
    private LocalDate extracarePlusDtLimitRenovation;

    @Column(name = "MC_CONTRACT_CODE")
    private String mcContractCode;

    @Column(name = "MC_CONTRACT_NAME")
    private String mcContractName;

    @Column(name = "MC_OID_DEALER_SELL")
    private String mcOidDealerSell;

    @Column(name = "MC_DT_SELL")
    private LocalDate mcDtSell;

    @Column(name = "MC_DEALER_PRICE")
    private Double mcDealerPrice;

    @Column(name = "MC_PVP_PRICE")
    private Double mcPvpPrice;

    @Column(name = "MC_CLIENT_PRICE")
    private Double mcClientPrice;

    @Column(name = "MC_DT_START_CONTRACT")
    private LocalDate mcDtStartContract;

    @Column(name = "MC_DT_END_CONTRACT")
    private LocalDate mcDtEndContract;

    @Column(name = "MC_DT_FINISH_CONTRACT")
    private LocalDate mcDtFinishContract;

    @Column(name = "TC_ID")
    private Integer tcId;

    @Column(name = "TC_ID_PA_DATA")
    private Integer tcIdPaData;

    @Column(name = "TC_CAMPAIGN")
    private String tcCampaign;

    @Column(name = "TC_SEND_DATE")
    private LocalDate tcSendDate;

    @Column(name = "TC_AGE")
    private Integer tcAge;

    @Column(name = "TC_GAMMA")
    private String tcGamma;

    @Column(name = "TC_TECHNICAL_MODEL")
    private String tcTechnicalModel;

    @Column(name = "TC_PHONE1")
    private String tcPhone1;

    @Column(name = "TC_PHONE2")
    private String tcPhone2;

    @Column(name = "TC_PHONE3")
    private String tcPhone3;

    @Column(name = "TC_SALES_DATE")
    private String tcSalesDate;

    @Column(name = "TC_CONTACT_SOURCE")
    private String tcContactSource;

    @Column(name = "TC_EXLUDE")
    private String tcExclude;

    @Column(name = "TC_GENERATION_DATE")
    private LocalDate tcGenerationDate;

    @Column(name = "TC_SEND_DATE_2")
    private LocalDate tcSendDate2;

    @Column(name = "TC_CREATED_BY")
    private String tcCreatedBy;

    @Column(name = "TC_DT_CREATED")
    private LocalDate tcDtCreated;

    @Column(name = "TC_CHANGED_BY")
    private String tcChangedBy;

    @Column(name = "TC_DT_CHANGED")
    private LocalDate tcDtChanged;


}

