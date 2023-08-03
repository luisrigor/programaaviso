package com.gsc.programaavisos.repository.crm.impl;

import com.gsc.programaavisos.dto.ItemFilter;
import com.gsc.programaavisos.model.crm.entity.DocumentUnit;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.repository.crm.DocumentUnitCustomRepository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class DocumentUnitCustomRepositoryImpl implements DocumentUnitCustomRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DocumentUnit> getByFilter(ItemFilter itemFilter) {
        StringBuilder SQL = new StringBuilder();
        SQL.append(" SELECT *, PDUC.DESCRIPTION AS CATEGORY_DESCRIPTION FROM PA_DOCUMENT_UNIT PDU, PA_DOCUMENT_UNIT_CATEGORY PDUC ");
        SQL.append(itemFilter.whereClause());
        SQL.append(" AND PDU.ID_DOCUMENT_UNIT_CATEGORY = PDUC.ID (+)");
        Query query = em.createNativeQuery(SQL.toString(), PaParameterization.class);
        itemFilter.prepareStatement(query, 1);
        List<DocumentUnit> documentUnitsList = query.getResultList();
        for(DocumentUnit documentUnit : documentUnitsList){
            /*if(documentUnit.g.getDescription().getString("CATEGORY_DESCRIPTION")!=null){
                documentUnit.set.setDocumentUnitCategory(rs.getString("CATEGORY_DESCRIPTION"));
            }*/
            documentUnitsList.add(documentUnit);
        }
        return documentUnitsList;

    }
}
