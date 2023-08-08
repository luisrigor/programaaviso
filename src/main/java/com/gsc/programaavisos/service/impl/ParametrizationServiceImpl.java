package com.gsc.programaavisos.service.impl;

import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.repository.crm.PaParameterizationRepository;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ParametrizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Log4j
@RequiredArgsConstructor
public class ParametrizationServiceImpl implements ParametrizationService {

    private final PaParameterizationRepository paParameterizationRepository;
    @Override
    public List<PaParameterization> searchParametrizations(Date startDate, Date endDate, String selectedTypeParam, UserPrincipal userPrincipal) {
        try {
            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());
            List<String> selectedTypes = new ArrayList<>();

            if(!StringUtils.isEmpty(selectedTypeParam))
                selectedTypes = Arrays.asList(selectedTypeParam.split(","));

            ParameterizationFilter filter = ParameterizationFilter.builder()
                    .dtStart(startDate)
                    .dtEnd(endDate)
                    .selectedTypes(selectedTypes)
                    .idBrand(idBrand)
                    .build();

            return  paParameterizationRepository.getByFilter(filter);
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching parametrization", e);
        }
    }

}
