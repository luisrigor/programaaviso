package com.gsc.programaavisos.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PADTO {
    Long id;
    String contactChanged;
    String dataIsCorrect;
    String newNif;
    String newName;
    String newAddress;
    String newAddressNumber;
    String newFloor;
    String newCp4;
    String newCp3;
    String newCpExt;
    String newContactPhone;
    String newEmail;
    String successContact;
    String successMotive;
    String dtScheduleContact;
    String hrScheduleContact;

    String recoveryAndShipping;

    String revisionSchedule;
    String revisionScheduleMotive;
    String km;
    String geralRevisionScheduleMotive2;
    String newsletterReceived;
    String notReceivedMotive;
    String idClientChannelPreference;
    String observations;
    String registerClaim;
    String sendSchedule;
    String oidDealerSchedule;
    String dtSchedule;
    String hrHrSchedule;
    String minHrSchedule;

}
