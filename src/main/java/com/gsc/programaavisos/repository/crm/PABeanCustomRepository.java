
package com.gsc.programaavisos.repository.crm;


import com.gsc.programaavisos.dto.FilterBean;

import com.gsc.programaavisos.model.crm.entity.PATotals;
import com.gsc.programaavisos.model.crm.entity.ProgramaAvisosBean;
import com.sc.commons.exceptions.SCErrorException;

import java.util.List;

public interface PABeanCustomRepository {

    List<ProgramaAvisosBean> getProgramaAvisosBean(FilterBean filterBean) throws SCErrorException;

    PATotals getPaTotals(FilterBean filterBean) throws SCErrorException;




}


