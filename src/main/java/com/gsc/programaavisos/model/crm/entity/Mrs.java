package com.gsc.programaavisos.model.crm.entity;

import lombok.*;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PA_MRS")
public class Mrs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ID_PA_DATA")
    private Integer idPaData;
    @Column(name = "DEALER_CODE")
    private String dealerCode;
    @Column(name = "AFTERSALES_CODE")
    private String afterSalesCode;
    @Column(name = "CONTACT_CHANNEL")
    private String contactChannel;
    @Column(name = "POSTAL_TYPE")
    private String postalType;
    @Column(name = "LAST_REVISION")
    private String lastRevision;
    @Column(name = "LAST_REVISION_KM")
    private String lastRevisionKm;
    @Column(name = "DT_LAST_REVISION")
    private Date dtLastRevision;
    @Column(name = "NEXT_REVISION")
    private String nextRevision;
    @Column(name = "YEAR_NEXT_REVISION")
    private Integer yearNextRevision;
    @Column(name = "MONTH_NEXT_REVISION")
    private Integer monthNextRevision;
    @Column(name = "DT_NEXT_REVISION")
    private Date dtNextRevision;
    @Column(name = "DT_ITV")
    private Date dtItv;
    @Column(name = "MAINTENANCE_PRICE")
    private Double maintenancePrice;
    @Column(name = "EUROCARE")
    private String eurocare;
    @Column(name = "FLAG_5_PLUS")
    private Character flag5Plus;
    @Column(name = "FLAG_SEND")
    private Character flagSend;
    @Column(name = "SKIN_DO_POSTAL")
    private String skinDoPostal;
    @Column(name = "CONTACT_REASON")
    private String contactReason;
    @Column(name = "FLAG_MAINTENANCE_CONTRACT")
    private Character flagMaintenanceContract;
    @Column(name = "FLAG_HYBRID")
    private Character flagHybrid;
    @Column(name = "ACESSORY_CODE_1")
    private String acessoryCode1;
    @Column(name = "ACESSORY_1")
    private String acessory1;
    @Column(name = "ACESSORY_1_DESC")
    private String acessory1Desc;
    @Column(name = "ACESSORY_1_LINK")
    private String acessory1Link;
    @Column(name = "ACESSORY_1_IMG_POSTAL")
    private String acessory1ImgPostal;
    @Column(name = "ACESSORY_1_IMG_E_POSTAL")
    private String acessory1ImgEPostal;
    @Column(name = "ACESSORY_CODE_2")
    private String acessoryCode2;
    @Column(name = "ACESSORY_2")
    private String acessory2;
    @Column(name = "ACESSORY_2_DESC")
    private String acessory2Desc;
    @Column(name = "ACESSORY_2_LINK")
    private String acessory2Link;
    @Column(name = "ACESSORY_2_IMG_POSTAL")
    private String acessory2ImgPostal;
    @Column(name = "ACESSORY_2_IMG_E_POSTAL")
    private String acessory2ImgEPostal;
    @Column(name = "SERVICE_CODE_1")
    private String serviceCode1;
    @Column(name = "SERVICE_1")
    private String service1;
    @Column(name = "SERVICE_CODE_2")
    private String serviceCode2;
    @Column(name = "SERVICE_2")
    private String service2;
    @Column(name = "SERVICE_CODE_3")
    private String serviceCode3;
    @Column(name = "SERVICE_3")
    private String service3;
    @Column(name = "CMK_DMV_1")
    private String cmkDmv1;
    @Column(name = "CMK_DMV_1_DT_END")
    private Date cmkDmv1DtEnd;
    @Column(name = "CMK_DMV_1_IMAGE")
    private String cmkDmv1Image;
    @Column(name = "CMK_DAV_2")
    private String cmkDav2;
    @Column(name = "CMK_DAV_2_DT_END")
    private Date cmkDav2DtEnd;
    @Column(name = "CMK_DAV_2_IMAGE")
    private String cmkDav2Image;
    @Column(name = "MAINTENANCE_PLAN")
    private String maintenancePlan;
    @Column(name = "MAINTENANCE_PRICE_DISCOUNT_PERC")
    private Double maintenancePriceDiscountPerc;
    @Column(name = "MAINTENANCE_PRICE_DISCOUNT_VALUE")
    private Double maintenancePriceDiscountValue;
    @Column(name = "SEND_TYPE")
    private Character sendType;
    @Column(name = "OPERATION_CODE")
    private String operationCode;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "DT_CREATED")
    private Timestamp dtCreated;
    @Column(name = "CHANGED_BY")
    private String changedBy;
    @Column(name = "DT_CHANGED")
    private Timestamp dtChanged;
    @Column(name = "IS_FIRST_REVISION")
    private Character isFirstRevision;
    @Column(name = "EXPECTED_KM")
    private String expectedKm;
    @Column(name = "GENRE")
    private String genre;
    @Column(name = "LAST_SERVICE_DEALER")
    private String lastServiceDealer;
    @Column(name = "LAST_SERVICE_DEALER_CONTACT")
    private String lastServiceDealerContact;
}
