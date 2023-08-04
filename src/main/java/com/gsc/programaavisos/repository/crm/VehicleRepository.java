package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query("SELECT V FROM Vehicle V WHERE UPPER(V.licencePlate) = :licencePlate")
    Vehicle getVehicle(@Param("licencePlate") String licencePlate);
}
