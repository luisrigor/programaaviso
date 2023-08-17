package com.gsc.programaavisos.repository.crm.impl;

import com.gsc.programaavisos.dto.MaintenanceTypeDTO;
import com.gsc.programaavisos.repository.crm.PACustomRepository;
import com.sc.commons.dbconnection.ServerJDBCConnection;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.DataBaseTasks;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

public class PACustomRepositoryImpl implements PACustomRepository {


    @PersistenceContext
    private EntityManager em;

    @Override
    public List<String> getDelegators(int fromYear, int fromMonth, int toYear, int toMonth, String oidDealer) {

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT PA_DELEGATED_TO FROM ( ");
        sql.append(" SELECT PA_DELEGATED_TO,COLLATION_KEY_BIT(PA_DELEGATED_TO, 'UCA500R1_S1') AS ORDER_FIELD ");
        sql.append(" FROM PA_DATA_INFO ");
        sql.append(" WHERE (");
        sql.append("(PA_YEAR > " + fromYear + " OR (PA_YEAR = " + fromYear + " AND PA_MONTH >= " + fromMonth + "))");
        sql.append(" AND (");
        sql.append("(PA_YEAR < " + toYear + " OR (PA_YEAR = " + toYear + " AND PA_MONTH <= " + toMonth + "))");
        sql.append("))");
        sql.append(" AND PA_OID_DEALER in (" + (oidDealer.equals("") ? "''" : oidDealer) + ") ");
        sql.append(" AND VALUE(PA_DELEGATED_TO,'') <> '' AND PA_VISIBLE = 'S' AND PA_DT_VISIBLE <= CURRENT DATE ");
        sql.append(" ORDER BY ORDER_FIELD");
        sql.append(" ) ");

        Query query = em.createNativeQuery(sql.toString());
        List<String> delegators = query.getResultList();

        return delegators;
    }

    @Override
    public Map<String, String> getLastChangedBy(int fromYear, int fromMonth, int toYear, int toMonth, String oidDealer) {

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT DISTINCT PA_CHANGED_BY, ");
        sql.append(" CASE WHEN U.NOME_COMERCIAL<>'' THEN U.NOME_COMERCIAL ELSE U.NOME_UTILIZADOR END AS NOME, ");
        sql.append(" COLLATION_KEY_BIT(CASE WHEN U.NOME_COMERCIAL<>'' THEN U.NOME_COMERCIAL ELSE U.NOME_UTILIZADOR END, 'UCA500R1_S1') AS ORDER_FIELD ");
        sql.append(" FROM PA_DATA_INFO ");
        sql.append(" LEFT JOIN USRLOGON_UTILIZADOR U ON PA_CHANGED_BY = CHAR(U.ID_UTILIZADOR) ");
        sql.append(" WHERE (");
        sql.append("(PA_YEAR > " + fromYear + " OR (PA_YEAR = " + fromYear + " AND PA_MONTH >= " + fromMonth + "))");
        sql.append(" AND (");
        sql.append("(PA_YEAR < " + toYear + " OR (PA_YEAR = " + toYear + " AND PA_MONTH <= " + toMonth + "))");
        sql.append("))");
        sql.append(" AND PA_OID_DEALER in (" + (oidDealer.equals("") ? "''" : oidDealer) + ") ");
        sql.append(" AND VALUE(PA_CHANGED_BY,'') <> '' ");
        sql.append(" AND U.NOME_UTILIZADOR IS NOT NULL ");
        sql.append(" ORDER BY ORDER_FIELD ");

        Query query = em.createNativeQuery(sql.toString());
        List<Object[]> data = query.getResultList();

        Map<String, String> mapLastChangedBy = new HashMap<>();

        for (Object[] currentRow: data) {
            mapLastChangedBy.put(currentRow[0]!=null ? (String) currentRow[0]: "", currentRow[1]!=null ? (String) currentRow[1]: "");
        }

        return mapLastChangedBy;
    }

    @Override
    public List<MaintenanceTypeDTO> getMaintenanceTypesByContactType() {

        StringBuilder sql  = new StringBuilder();
        sql.append(" SELECT PAC.ID AS ID, PAC.NAME AS CONTRACT_TYPE, PAM.NAME AS MAINTENANCE_TYPE ");
        sql.append(" FROM PA_CONTACTTYPE_MAINTENANCETYPES PACM ");
        sql.append(" LEFT JOIN PA_CONTACTTYPE PAC ON PAC.ID = PACM.ID_CONTACTTYPE AND PAC.STATUS = 'S' ");
        sql.append(" LEFT JOIN PA_MRS_MAINTENANCETYPES PAM ON PAM.ID = PACM.ID_MRS_MAINTENANCETYPES ");
        sql.append(" ORDER BY PAC.ID, PAM.PRIORITY, PAM.NAME ");

        Query query = em.createNativeQuery(sql.toString(),"MaintenanceTypeMapping");
        List<MaintenanceTypeDTO> data = query.getResultList();

        return data;


    }
}
