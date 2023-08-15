package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ItemsGenre;
import com.gsc.programaavisos.model.crm.entity.ItemsKilometers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsKilometersRepository extends JpaRepository<ItemsKilometers,Integer> {

    List<ItemsKilometers> findByIdParameterizationItems(Integer id);
}
