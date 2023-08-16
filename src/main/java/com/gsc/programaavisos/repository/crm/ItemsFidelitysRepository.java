package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ItemsFidelitys;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsFidelitysRepository extends JpaRepository<ItemsFidelitys,Integer> {
    List<ItemsFidelitys> findByIdParameterizationItems(Integer id);
}
