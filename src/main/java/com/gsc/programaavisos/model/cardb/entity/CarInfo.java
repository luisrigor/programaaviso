package com.gsc.programaavisos.model.cardb.entity;

import com.gsc.cardb.car.Car;
import lombok.*;

import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarInfo {

    private int idModel;
    private int identity;
    private String nif;
    private int idGender;
    private String gender;
    private int idFuel;
    private String fuel;
    private int idAge;
    private String plateDate;
    private int idVc;
    private int idKilometers;
    private String kilometers;
    private int idFidelity;
    private String Dealer;
    private String numberPlate;
    private int idContactReason;
    private LocalDate dtNextRevision;
    private LocalDate dtItv;
    private LocalDate dtLastRevision;
    private String dtLastRevisionString;
    private String lastServiceDealer;
    private String lastServiceDealerContact;
    @Transient
    private com.gsc.cardb.car.Car car;
    @Transient
    protected com.gsc.ws.core.CarInfo as400CarInfo;
}
