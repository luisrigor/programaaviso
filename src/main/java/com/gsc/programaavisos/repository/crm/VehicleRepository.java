package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
