package com.gsc.programaavisos.repository.crm.impl;

import com.gsc.programaavisos.config.AliasToEntityMapResultTransformer;
import com.gsc.programaavisos.dto.FilterBean;
import com.gsc.programaavisos.dto.ProgramaAvisosBean;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.repository.crm.PACustomRepository;
import com.sc.commons.dbconnection.ServerJDBCConnection;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.DataBaseTasks;
import com.sc.commons.utils.StringTasks;
import org.hibernate.query.internal.NativeQueryImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public List<ProgramaAvisosBean> getOpenContactsforClient(FilterBean oFilter, String nif, String licencePlate,
                                                             Map<Integer, List<String>> getMaintenanceTypesByContactType) {

        List<ProgramaAvisosBean> vecPABean = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        try {

            FilterBean tmpFilterBean = null;
            try {
                tmpFilterBean = (FilterBean) oFilter.clone();
                tmpFilterBean.setFromYear(2013);
                tmpFilterBean.setFromMonth(1);
                tmpFilterBean.setToYear(9999);
                tmpFilterBean.setToMonth(12);
                tmpFilterBean.setIdContactType(0);
                tmpFilterBean.setFlagHibrid("");
                tmpFilterBean.setPlate("");
                tmpFilterBean.clearState();
                tmpFilterBean.setStatePending(1);
                tmpFilterBean.setStateHasSchedule(1);
            } catch (CloneNotSupportedException e) {
            }
            if(tmpFilterBean!=null){
                sql.append(" SELECT * FROM PA_DATA_INFO " + tmpFilterBean.getWhereClause(getMaintenanceTypesByContactType) + " AND (1=2 ");
                if (!StringTasks.cleanString(nif, "").equals(""))
                    sql.append(" OR PA_NIF = '" + nif + "'");

                if (!StringTasks.cleanString(licencePlate, "").equals(""))
                    sql.append(" OR PA_LICENSE_PLATE = '" + StringTasks.ReplaceStr(licencePlate, "-", "").trim().toUpperCase() + "' ");

                sql.append(") ORDER BY PA_ID");
            }

            Query query = em.createNativeQuery(sql.toString());


            NativeQueryImpl nativeQuery = (NativeQueryImpl) query;
            nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
            List<Map<String,Object>> result = nativeQuery.getResultList();

            for (Map<String,Object> currentRs: result) {
                vecPABean.add(new ProgramaAvisosBean(currentRs, false));
            }
            return vecPABean;
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error executing getOpenContactsforClient query ", e);
        }
    }

}
