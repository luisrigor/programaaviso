package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ItemsModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsModelRepository extends JpaRepository<ItemsModel,Integer> {
    List<ItemsModel> findByIdParameterizationItems(Integer id);

}
