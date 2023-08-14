package com.gsc.programaavisos.model.crm.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PA_CONTACTTYPE")
public class ContactType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "STATUS")
    private Character status;
    @Column(name = "ORDER_COLUMN")
    private String orderColumn;
    @Column(name = "ORDER_COLUMN_DESCRIPTION")
    private String orderColumnDescription;
}
