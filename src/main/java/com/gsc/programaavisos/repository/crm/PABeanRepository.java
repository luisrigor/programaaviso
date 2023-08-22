package com.gsc.programaavisos.repository.crm;

import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PABeanRepository extends JpaRepository<ProgramaAvisosBean,Integer>, PABeanCustomRepository {

    @Query(value= "SELECT PA_DATA_INFO.*, HHC.PRODUCT_ID AS HHC_PRODUCT_ID, " +
            "HHC.PRODUCT_DESCRIPTION AS HHC_PRODUCT_DESCRIPTION,HHC.PRODUCT_DISPLAY_NAME AS HHC_PRODUCT_DISPLAY_NAME, " +
            "HHC.CONTRACT_START_DATE AS HHC_CONTRACT_START_DATE, HHC.CONTRACT_END_DATE AS HHC_CONTRACT_END_DATE, " +
            "(HHC.MILEAGE_CONTRACT_CREATION + HHC.COVER_KM) AS HHC_CONTRACT_END_KM FROM PA_DATA_INFO " +
            "LEFT JOIN VEHICLE_HHC HHC ON HHC.CONTRACT_VIN = PA_DATA_INFO.PA_VIN AND HHC.CONTRACT_STATUS='Active' " +
            "WHERE PA_DATA_INFO.PA_ID = :id",nativeQuery = true)
    ProgramaAvisosBean getProgramaAvisosBeanById(@Param("id") Integer id);
}
