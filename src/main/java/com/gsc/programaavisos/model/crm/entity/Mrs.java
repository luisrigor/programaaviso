package com.gsc.programaavisos.model.crm.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String aftersalesCode;

    @Column(name = "CONTACT_CHANNEL")
    private String contactChannel;

    @Column(name = "POSTAL_TYPE")
    private String postalType;

    @Column(name = "LAST_REVISION")
    private String lastRevision;

    @Column(name = "LAST_REVISION_KM")
    private String lastRevisionKm;

    @Column(name = "DT_LAST_REVISION")
    private LocalDate dtLastRevision;

    @Column(name = "NEXT_REVISION")
    private String nextRevision;

    @Column(name = "YEAR_NEXT_REVISION")
    private Integer yearNextRevision;

    @Column(name = "MONTH_NEXT_REVISION")
    private Integer monthNextRevision;

    @Column(name = "DT_ITV")
    private LocalDate dtItv;

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
    private String accessoryCode1;

    @Column(name = "ACESSORY_1")
    private String acessory1;

    @Column(name = "ACESSORY_CODE_2")
    private String accessoryCode2;

    @Column(name = "ACESSORY_2")
    private String acessory2;

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

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "DT_CHANGED")
    private LocalDateTime dtChanged;

    @Column(name = "CMK_DMV_1")
    private String cmkDmv1;

    @Column(name = "CMK_DMV_1_DT_END")
    private LocalDate cmkDmv1DtEnd;

    @Column(name = "CMK_DMV_1_IMAGE")
    private String cmkDmv1Image;

    @Column(name = "CMK_DAV_2")
    private String cmkDav2;

    @Column(name = "CMK_DAV_2_DT_END")
    private LocalDate cmkDav2DtEnd;

    @Column(name = "CMK_DAV_2_IMAGE")
    private String cmkDav2Image;

    @Column(name = "MAINTENANCE_PLAN")
    private String maintenancePlan;

    @Column(name = "MAINTENANCE_PRICE_DISCOUNT_PERC")
    private Double maintenancePriceDiscountPerc;

    @Column(name = "MAINTENANCE_PRICE_DISCOUNT_VALUE")
    private Double maintenancePriceDiscountValue;

    @Column(name = "DT_NEXT_REVISION")
    private LocalDate dtNextRevision;

    @Column(name = "IS_FIRST_REVISION")
    private Character isFirstRevision;

    @Column(name = "SEND_TYPE")
    private Character sendType;

    @Column(name = "OPERATION_CODE")
    private String operationCode;

    @Column(name = "SENT_INFO")
    private String sentInfo;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;

    @Column(name = "EXPECTED_KM")
    private String expectedKm;

    @Column(name = "GENRE")
    private String genre;

    @Column(name = "ACESSORY_1_DESC")
    private String acessory1Desc;

    @Column(name = "ACESSORY_1_LINK")
    private String accessory1Link;

    @Column(name = "ACESSORY_1_IMG_POSTAL")
    private String acessory1ImgPostal;

    @Column(name = "ACESSORY_1_IMG_E_POSTAL")
    private String accessory1ImgEPostal;

    @Column(name = "ACESSORY_2_DESC")
    private String acessory2Desc;

    @Column(name = "ACESSORY_2_LINK")
    private String accessory2Link;

    @Column(name = "ACESSORY_2_IMG_POSTAL")
    private String acessory2ImgPostal;

    @Column(name = "ACESSORY_2_IMG_E_POSTAL")
    private String accessory2ImgEPostal;

    @Column(name = "LAST_SERVICE_DEALER")
    private String lastServiceDealer;

    @Column(name = "LAST_SERVICE_DEALER_CONTACT")
    private String lastServiceDealerContact;
}
