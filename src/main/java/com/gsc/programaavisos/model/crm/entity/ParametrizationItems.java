package com.gsc.programaavisos.model.crm.entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "PA_PARAMETERIZATION_ITEMS")
public class ParametrizationItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "ID_PARAMETERIZATION")
    private Integer idParameterization;
    @Column(name = "ID_HIGHLIGHT_1")
    private Integer idHighlight1;
    @Column(name = "ID_HIGHLIGHT_2")
    private Integer idHighlight2;
    @Column(name = "ID_HIGHLIGHT_3")
    private Integer idHighlight3;
    @Column(name = "ID_SERVICE_1")
    private Integer idService1;
    @Column(name = "ID_SERVICE_2")
    private Integer idService2;
    @Column(name = "ID_SERVICE_3")
    private Integer idService3;
    @Column(name = "ID_HEADER_1")
    private Integer idHeader1;
    @Column(name = "ID_HEADER_2")
    private Integer idHeader2;
    @Column(name = "ID_HEADER_3")
    private Integer idHeader3;
    @Column(name = "PARAMETERIZATION_ITEMS_TYPE")
    private String parameterizationItemsType;
    @Column(name = "ID_CONTACT_REASON")
    private Integer idContactReason;
    @Column(name = "STATUS")
    private Character status;
    @Column(name = "CREATED_BY")
    private String createdBy;
    @Column(name = "DT_CREATED")
    private LocalDateTime dtCreated;
    @Column(name = "CHANGED_BY")
    private String changedBy;
    @Column(name = "DT_CHANGED")
    private LocalDateTime dtChanged;

    @Transient
    private List<ItemsAge> itemAges;
    @Transient
    private List<ItemsKilometers> itemKilometers;
    @Transient
    private List<ItemsFidelitys> itemFidelitys;
    @Transient
    private List<ItemsGenre> itemGenres;
    @Transient
    private List<ItemsEntityType> itemEntityTypes;
    @Transient
    private List<ItemsFuel> itemFuels;
    @Transient
    private List<ItemsModel> itemModels;
    @Transient
    private List<ItemsDealer> itemDealers;
}
