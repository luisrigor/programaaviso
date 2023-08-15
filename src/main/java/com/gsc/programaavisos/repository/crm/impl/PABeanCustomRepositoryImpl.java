package com.gsc.programaavisos.repository.crm.impl;

import com.gsc.programaavisos.config.ApplicationConfiguration;
import com.gsc.programaavisos.dto.FilterBean;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.PATotals;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import com.gsc.programaavisos.repository.crm.PABeanCustomRepository;
import com.sc.commons.dbconnection.ServerJDBCConnection;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.DataBaseTasks;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

public class PABeanCustomRepositoryImpl implements PABeanCustomRepository {

    public static final int PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS = 5;
    public static final int PAGE_SIZE = 20;

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<ProgramaAvisosBean> getProgramaAvisosBean(FilterBean filterBean) throws SCErrorException {
        String sql = buildPAListQuery(filterBean);
        Query query = em.createNativeQuery(sql, ProgramaAvisosBean.class);
        List<ProgramaAvisosBean> programaAvisosBeanList = query.getResultList();
        return programaAvisosBeanList;
    }

    @Override
    public PATotals getPaTotals(FilterBean filterBean) throws SCErrorException {
        String sql = buildPATotalsQuery(filterBean);
        Query query = em.createNativeQuery(sql,"GetPATotalsMapping");
        PATotals paTotals = (PATotals) query.getSingleResult();
        System.out.println(paTotals.toString());
        return paTotals;
    }

    public String buildPAListQuery(@NotNull FilterBean oFilter) throws SCErrorException {
        StringBuilder sql = new StringBuilder();

        int pageStart = ((oFilter.getCurrPage() - 1) * PAGE_SIZE) + 1;
        int pageEnd = (oFilter.getCurrPage() * PAGE_SIZE);
        boolean showAllRecords = true;
        if (showAllRecords) {
            pageStart = 1;
            pageEnd = 999999999;
        }

        boolean checkExpiredContracts = oFilter.getIdContactType() == ApplicationConfiguration.PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS;
        boolean checkConnectivityContracts = oFilter.getIdContactType() == ApplicationConfiguration.PA_CONTACTTYPE_CONECTIVIDADE;

        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.YEAR, oFilter.getFromYear());
        calStart.set(Calendar.MONTH, oFilter.getFromMonth() - 1);
        calStart.set(Calendar.DAY_OF_MONTH, 1);
        java.sql.Date dtStart = new java.sql.Date(calStart.getTimeInMillis());
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(Calendar.YEAR, oFilter.getToYear());
        calEnd.set(Calendar.MONTH, oFilter.getToMonth() - 1);
        calEnd.set(Calendar.DAY_OF_MONTH, calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
        java.sql.Date dtEnd = new java.sql.Date(calEnd.getTimeInMillis());

        //OFFSET de 20 em 20 comen�ando em 0. pageEnd come�a a 20.
        int offset = pageEnd - 20;
        int defaultTransactionIsolation = Integer.MIN_VALUE;
        sql.append("SELECT * FROM ( ");

        //Seteo Paginacion
        System.out.println("checkConnectivityContracts "+ checkConnectivityContracts);
       if (checkConnectivityContracts) {
                sql.append(" SELECT ROW_NUMBER() OVER( ORDER BY PA_DT_CREATED ) AS ROWNUM, ");
            } else {
                sql.append(" SELECT ROW_NUMBER() OVER( " + oFilter.getOrderBy() + " ) AS ROWNUM, ");
            }

        sql.append("SELECT ROW_NUMBER() OVER( ORDER BY PA_DT_CREATED ) AS ROWNUM, ");
        sql.append("PA_DATA_INFO.*, ");
        sql.append("HHC.PRODUCT_ID AS HHC_PRODUCT_ID, HHC.PRODUCT_DESCRIPTION AS HHC_PRODUCT_DESCRIPTION, HHC.PRODUCT_DISPLAY_NAME AS HHC_PRODUCT_DISPLAY_NAME, " +
                "HHC.CONTRACT_START_DATE AS HHC_CONTRACT_START_DATE, HHC.CONTRACT_END_DATE AS HHC_CONTRACT_END_DATE, " +
                "(HHC.MILEAGE_CONTRACT_CREATION + HHC.COVER_KM) AS HHC_CONTRACT_END_KM ");
        sql.append("FROM PA_DATA_INFO ");
        sql.append("LEFT JOIN VEHICLE_HHC HHC ON HHC.CONTRACT_VIN = PA_DATA_INFO.PA_VIN ");
        sql.append("AND HHC.CONTRACT_STATUS = 'Active' ");

        sql.append(oFilter.getWhereClause());

        if(checkExpiredContracts) {
                sql.append(" ) WHERE MC_DT_FINISH_CONTRACT >= '" + dtStart + "' AND MC_DT_FINISH_CONTRACT < '" + dtEnd + "' ");
                sql.append(" OFFSET " + offset + " ROWS FETCH NEXT 20 ROWS ONLY");
            } else {
                sql.append(" )WHERE ROWNUM BETWEEN " + pageStart + " AND " + pageEnd + " ");
            }
        sql.append(") AS Subquery;");



        System.out.println(String.valueOf(sql));
        return String.valueOf(sql);
    }

    public String buildPATotalsQuery(FilterBean filter) throws SCErrorException {

        StringBuffer sql = new StringBuffer();

        boolean checkExpiredContracts = filter.getIdContactType() == PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS;
        Calendar calStart = Calendar.getInstance();
        calStart.set(Calendar.YEAR, filter.getFromYear());
        calStart.set(Calendar.MONTH, filter.getFromMonth() - 1);
        calStart.set(Calendar.DAY_OF_MONTH, 1);
        java.sql.Date dtStart = new java.sql.Date(calStart.getTimeInMillis());


        Calendar calEnd = Calendar.getInstance();
        calEnd.set(Calendar.YEAR, filter.getToYear());
        calEnd.set(Calendar.MONTH, filter.getToMonth() - 1);
        calEnd.set(Calendar.DAY_OF_MONTH, calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
        java.sql.Date dtEnd = new java.sql.Date(calEnd.getTimeInMillis());

        int defaultTransactionIsolation = Integer.MIN_VALUE;

        sql.append(" SELECT COUNT(*) AS TOTAL, ");
        sql.append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=1 					THEN 1 ELSE 0 END),0) AS NOT_DONE, ");
        sql.append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS IN (3, 4, 5, 6, 7) THEN 1 ELSE 0 END),0) AS DONE, ");
        sql.append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=3 					THEN 1 ELSE 0 END),0) AS WITH_APPOINTMENT,  ");
        sql.append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=2 					THEN 1 ELSE 0 END),0) AS SCHEDULE,  ");
        sql.append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=8 					THEN 1 ELSE 0 END),0) AS REMOVED_MANUALLY, ");
        sql.append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=9 					THEN 1 ELSE 0 END),0) AS REMOVED_AUTO_BY_MANUT, ");
        sql.append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=10 				THEN 1 ELSE 0 END),0) AS REMOVED_AUTO_BY_PERIOD  ");

        sql.append(" FROM " + "PA_DATA_INFO ");
        sql.append(" LEFT JOIN VEHICLE_HHC HHC" + " ON HHC.CONTRACT_VIN = " + "PA_DATA_INFO" + ".PA_VIN AND HHC.CONTRACT_STATUS='Active' ");

     //   sql.append(filter.getWhereClauseForTotals());

        if (checkExpiredContracts) {
            sql.append(" AND MC_DT_FINISH_CONTRACT >= '" + dtStart + "' AND MC_DT_FINISH_CONTRACT < '" + dtEnd + "' ");
        }
        return String.valueOf(sql);
    }







}
