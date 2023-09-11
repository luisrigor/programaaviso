package com.gsc.programaavisos.model.cardb.entity;

import com.gsc.scwscardb.sc.model.*;
import com.gsc.ws.core.CombinationColors;
import com.gsc.ws.core.ComercialVersion;
import lombok.*;

import java.util.Vector;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {

    private int idBrand;
    private int idVc;
    private String brand;
    private Generation generation;
    private Model model;
    private Version version;
    private ComercialVersion comercialVersion;
    //private ComercialVersionCodes comercialVersionCodes;
    private Category category;
    private Vector<Equipment> equipment;
    private Vector<Color> externalColors;
    private Vector<Color> internalColorsForExternalColor;
    private Vector<CombinationColors> combinationColors;
    private Prices prices;
    private Specification specification;
    private Painting painting;
    private boolean includesMetalPainting;
    private boolean isHybrid;
    private MaintenancePlan maintenancePlan;

}
