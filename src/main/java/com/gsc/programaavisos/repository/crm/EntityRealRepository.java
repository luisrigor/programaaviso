package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.EntityReal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntityRealRepository extends JpaRepository<EntityReal, Integer> {


    @Query("SELECT ER " +
    "FROM EntityReal ER " +
    "INNER JOIN VehicleReal VR ON VR.idUser = ER.id " +
    "WHERE UPPER(VR.licencePlate)= :plate")
    EntityReal getVehicleUser(@Param("plate") String plate);

    @Query("SELECT ER " +
            "FROM EntityReal ER " +
            "INNER JOIN VehicleReal VR ON VR.idOwner = ER.id " +
            "WHERE UPPER(VR.licencePlate)= :plate")
    EntityReal getVehicleOwner(@Param("plate") String plate);


}
