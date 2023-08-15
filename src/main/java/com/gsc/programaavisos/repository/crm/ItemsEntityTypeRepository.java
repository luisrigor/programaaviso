package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ItemsEntityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsEntityTypeRepository extends JpaRepository<ItemsEntityType,Integer> {

    List<ItemsEntityType> findByIdParameterizationItems(Integer id);
}
