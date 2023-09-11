package com.gsc.programaavisos.dto;

import com.gsc.programaavisos.model.cardb.entity.CarInfo;
import com.gsc.programaavisos.model.crm.entity.Mrs;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisos;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TpaSimulation {

    private String contactReason;
    private String service1Link;
    private String service1Name;
    private String service1Desc;
    private String service1ImgPostal;
    private String service1ImgEPostal;
    private String service1Code;
    private String service2Link;
    private String service2Name;
    private String service2Desc;
    private String service2ImgPostal;
    private String service2ImgEPostal;
    private String service2Code;
    private String service3Link;
    private String service3Name;
    private String service3Desc;
    private String service3ImgPostal;
    private String service3ImgEPostal;
    private String service3Code;
    private String servicoOriginParameterization;
    private String highlight1Link;
    private String highlight1Name;
    private String highlight1Desc;
    private String highlight1ImgPostal;
    private String highlight1ImgEPostal;
    private String highlight1Code;
    private String highlight2Link;
    private String highlight2Name;
    private String highlight2Desc;
    private String highlight2ImgPostal;
    private String highlight2ImgEPostal;
    private String highlight2Code;
    private String destaqueOriginParameterization;
    private String header1Name;
    private String header1ImgPostal;
    private String header1ImgEPostal;
    private String header1Code;
    private String header1Link;
    private String header2Name;
    private String header2ImgPostal;
    private String header2ImgEPostal;
    private String header2Code;
    private String header2Link;
    private String header3Name;
    private String header3ImgPostal;
    private String header3ImgEPostal;
    private String header3Code;
    private String header3Link;
    private String headerOriginParameterization;
    private String accessory1Link;
    private String accessory1Name;
    private String accessory1Desc;
    private String accessory1ImgPostal;
    private String accessory1ImgEPostal;
    private String accessory1Code;
    private String accessory2Link;
    private String accessory2Name;
    private String accessory2Desc;
    private String accessory2ImgPostal;
    private String accessory2ImgEPostal;
    private String accessory2Code;
    //protected List<com.gsc.programaavisos.core.bo.CarInfo> businessCarInfo;
    private CarInfo carInfo;
    private List<CarInfo> businessCarInfo;
    private ProgramaAvisos paData;
    private Mrs mrs;

}
