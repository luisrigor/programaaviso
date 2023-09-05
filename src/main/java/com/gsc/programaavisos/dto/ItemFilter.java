package com.gsc.programaavisos.dto;

import com.sc.commons.utils.DataBaseTasks;
import lombok.*;

import javax.persistence.Query;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemFilter {

    private int itemType;
    protected int idBrand;
    protected String searchInput;
    protected Date dtEnd;

    public String whereClause() {

        int paramCounter = 1;

        StringBuilder clause = new StringBuilder(" WHERE 1 = 1 ");
        clause.append(" AND PDU.ID_DOCUMENT_UNIT_TYPE = ?" + paramCounter++);
        clause.append(" AND PDU.ID_BRAND = ?" +paramCounter++);
        if (searchInput != null && !"".equals(searchInput)) {
            clause.append(" AND (upper(PDU.NAME) LIKE ?"+paramCounter++ +" OR upper(PDU.CODE) LIKE ?"+paramCounter++ + ") ");
        }
        if(dtEnd!=null) {
            clause.append(" AND (PDU.DT_END > ?"+paramCounter +" OR  PDU.DT_END IS NULL)");
        }
        return clause.toString();
    }

    public void prepareStatement(Query query, int pos) {
        query.setParameter(pos++, itemType);
        query.setParameter(pos++, idBrand);
        if (searchInput!= null &&!"".equals(searchInput)) {
            query.setParameter(pos++, DataBaseTasks.prepareStringToLike(searchInput));
            query.setParameter(pos++, DataBaseTasks.prepareStringToLike(searchInput));
        }
        if(dtEnd!=null) {
            query.setParameter(pos++, dtEnd);
        }
    }

}
