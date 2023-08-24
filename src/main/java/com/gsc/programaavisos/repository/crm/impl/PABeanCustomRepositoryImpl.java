package com.gsc.programaavisos.repository.crm.impl;

import com.gsc.programaavisos.config.ApplicationConfiguration;
import com.gsc.programaavisos.dto.FilterBean;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.PATotals;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import com.gsc.programaavisos.repository.crm.PABeanCustomRepository;
import com.gsc.programaavisos.util.PAUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Date;

import java.util.List;

public class PABeanCustomRepositoryImpl implements PABeanCustomRepository {

    public static final int PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS = 5;
    public static final int PAGE_SIZE = 20;

    @PersistenceContext
    private EntityManager em;
    @Override
    public List<ProgramaAvisosBean> getProgramaAvisosBean(FilterBean filterBean)  {
        try {
            String sql = buildPAListQuery(filterBean);
            Query query = em.createNativeQuery(sql, ProgramaAvisosBean.class);
            List<ProgramaAvisosBean> programaAvisosBeanList = query.getResultList();
            return programaAvisosBeanList;
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching ProgramaAvisosBean", e);
        }
    }

    @Override
    public PATotals getPaTotals(FilterBean filterBean) {
        try {
            String sql = buildPATotalsQuery(filterBean);
            Query query = em.createNativeQuery(sql,"GetPATotalsMapping");
            PATotals paTotals = (PATotals) query.getSingleResult();
            return paTotals;
        } catch (Exception e){
            throw new ProgramaAvisosException("Error fetching PATotals", e);
        }
    }

    public String buildPAListQuery(FilterBean oFilter) {

        StringBuilder sql = new StringBuilder();
        int pageStart = ((oFilter.getCurrPage() - 1) * PAGE_SIZE) + 1;
        int pageEnd = (oFilter.getCurrPage() * PAGE_SIZE);

        boolean checkExpiredContracts = oFilter.getIdContactType() == ApplicationConfiguration.PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS;
        boolean checkConnectivityContracts = oFilter.getIdContactType() == ApplicationConfiguration.PA_CONTACTTYPE_CONECTIVIDADE;

        Date dtStart = PAUtil.getDateFromFilter(oFilter);
        Date dtEnd = PAUtil.getDateFromFilter(oFilter);
        int offset = pageEnd - 20;

        sql.append("SELECT * FROM ( ");

        if (checkConnectivityContracts)
            sql.append(" SELECT ROW_NUMBER() OVER( ORDER BY PA_DT_CREATED ) AS ROWNUM, ");
        else
            sql.append(" SELECT ROW_NUMBER() OVER( ").append(oFilter.getOrderBy()).append(" ) AS ROWNUM, ");

        sql.append("PA_DATA_INFO.*, HHC.PRODUCT_ID AS HHC_PRODUCT_ID, ")
                .append("HHC.PRODUCT_DESCRIPTION AS HHC_PRODUCT_DESCRIPTION, HHC.PRODUCT_DISPLAY_NAME AS HHC_PRODUCT_DISPLAY_NAME, ")
                .append("HHC.CONTRACT_START_DATE AS HHC_CONTRACT_START_DATE, HHC.CONTRACT_END_DATE AS HHC_CONTRACT_END_DATE, " )
                .append("(HHC.MILEAGE_CONTRACT_CREATION + HHC.COVER_KM) AS HHC_CONTRACT_END_KM ")
                .append("FROM PA_DATA_INFO ").append("LEFT JOIN VEHICLE_HHC HHC ON HHC.CONTRACT_VIN = PA_DATA_INFO.PA_VIN ")
                .append("AND HHC.CONTRACT_STATUS = 'Active'");
        sql.append(")");

        if (checkExpiredContracts)
            sql.append(" WHERE MC_DT_FINISH_CONTRACT >= '").append(dtStart).append("' AND MC_DT_FINISH_CONTRACT < '")
                    .append(dtEnd).append("' ").append(" OFFSET ").append(offset).append(" ROWS FETCH NEXT 20 ROWS ONLY");
        else
            sql.append(" WHERE ROWNUM BETWEEN ").append(pageStart).append(" AND ").append(pageEnd).append(" ");

        return String.valueOf(sql);
    }

    public String buildPATotalsQuery(FilterBean filter) {

        boolean checkExpiredContracts = filter.getIdContactType() == PA_CONTACTTYPE_CONTRATOS_MANUTENCAO_EXPIRADOS;
        Date dtStart = PAUtil.getDateFromFilter(filter);
        Date dtEnd = PAUtil.getDateFromFilter(filter);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(*) AS TOTAL, COALESCE(SUM(CASE WHEN PA_ID_STATUS=1 THEN 1 ELSE 0 END),0) AS NOT_DONE,")
                .append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS IN (3, 4, 5, 6, 7) THEN 1 ELSE 0 END),0) AS DONE, ")
                .append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=3 THEN 1 ELSE 0 END),0) AS WITH_APPOINTMENT, ")
                .append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=2 THEN 1 ELSE 0 END),0) AS SCHEDULE, ")
                .append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=8 THEN 1 ELSE 0 END),0) AS REMOVED_MANUALLY, ")
                .append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=9 THEN 1 ELSE 0 END),0) AS REMOVED_AUTO_BY_MANUT, ")
                .append(" COALESCE(SUM(CASE WHEN PA_ID_STATUS=10 THEN 1 ELSE 0 END),0) AS REMOVED_AUTO_BY_PERIOD  ")
                .append(" FROM PA_DATA_INFO ")
                .append(" LEFT JOIN VEHICLE_HHC HHC ON HHC.CONTRACT_VIN = PA_DATA_INFO.PA_VIN AND HHC.CONTRACT_STATUS='Active' ");

        if (checkExpiredContracts)
            sql.append(" AND MC_DT_FINISH_CONTRACT >= '").append(dtStart).append("' AND MC_DT_FINISH_CONTRACT < '").append(dtEnd).append("' ");

        return String.valueOf(sql);
    }

}
