package com.gsc.programaavisos.model.crm.entity;

import lombok.*;
import org.apache.log4j.Logger;

import javax.persistence.Entity;
import javax.persistence.Table;
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
    private final static Logger logger = Logger.getLogger(Mrs.class.getName());

    private int id;
    private int idPaData;
    private String dealerCode;
    private String afterSalesCode;
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
    private String flagHybrid;
    private String acessoryCode1;
    private String acessory1;
    private String acessory1Desc;
    private String acessory1Link;
    private String acessory1ImgPostal;
    private String acessory1ImgEPostal;
    private String acessoryCode2;
    private String acessory2;
    private String acessory2Desc;
    private String acessory2Link;
    private String acessory2ImgPostal;
    private String acessory2ImgEPostal;
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
    private Double maintenancePriceDiscountPerc;
    private Double maintenancePriceDiscountValue;
    private String sendType;
    private String operationCode;
    private String createdBy;
    private Timestamp dtCreated;
    private String changedBy;
    private Timestamp dtChanged;
    private boolean isFirstRevision;
    private String expectedKm;
    private String genre;
    private String lastServiceDealer;
    private String lastServiceDealerContact;

}
