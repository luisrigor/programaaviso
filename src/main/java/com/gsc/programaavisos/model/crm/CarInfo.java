package com.gsc.programaavisos.model.crm;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
        private String dealer;
        private String numberplate;
        private int idContactReason;
        private LocalDate dtNextRevision;
        private LocalDate dtItv;
        private LocalDate dtLastRevision;
        private String dtLastRevisionString;
        private String lastServiceDealer;
        private String lastServiceDealerContact;

        protected com.gsc.cardb.car.Car car;
        protected com.gsc.ws.core.CarInfo as400CarInfo;
}
