package com.gsc.programaavisos.dto;


import lombok.*;


import javax.persistence.Query;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParameterizationFilter {

    protected List<String> selectedTypes;
    protected Date dtStart;
    protected Date dtEnd;
    protected Integer idBrand;

    public String whereClause() {
        StringBuilder clause = new StringBuilder("");
        Integer paramCounter = 1;

        clause.append(" WHERE 1 = 1 ");

        //Parametriza��o
        if (selectedTypes != null && selectedTypes.size() > 0 && !"".equalsIgnoreCase(selectedTypes.get(0))) {
            clause.append(" AND TYPE IN ( ");
            for (int i = 0 ; i < selectedTypes.size(); i++){
                if(!"".equalsIgnoreCase(selectedTypes.get(i))){
                    clause.append(" ?" +paramCounter++);
                }
                if((i+1) < selectedTypes.size()){
                    clause.append(",");
                }
            }
            clause.append(")");
        }

        //Brand
        if(idBrand >0){
            clause.append(" AND ID_BRAND = ?"+paramCounter++);
        }

        //Data
        if(dtStart!=null && dtEnd!=null) {
            clause.append(" AND DT_START BETWEEN ?"+paramCounter++ +" AND ?"+paramCounter++);
        } else if(dtStart!=null) {
            clause.append(" AND DT_START > ?"+paramCounter++);
        } else if(dtEnd!=null) {
            clause.append(" AND DT_START < ? "+paramCounter);
        }

        return clause.toString();
    }

    public void prepareStatement(Query query, int pos) {
        //Parametriza��o
        if (selectedTypes != null && selectedTypes.size() > 0) {
            for(String type : selectedTypes){
                if(!"".equalsIgnoreCase(type)){
                    query.setParameter(pos++, type);
                }
            }
        }

        //Brand
        if(idBrand>0){
            query.setParameter(pos++, idBrand);
        }

        //Data
        if(dtStart!=null && dtEnd!=null) {
            query.setParameter(pos++, dtStart);
            query.setParameter(pos++, dtEnd);
        } else if(dtStart!=null) {
            query.setParameter(pos++, dtStart);
        } else if(dtEnd!=null) {
            query.setParameter(pos++, dtEnd);
        }
    }
}
