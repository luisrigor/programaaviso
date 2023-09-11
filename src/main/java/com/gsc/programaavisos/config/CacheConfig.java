package com.gsc.programaavisos.config;

import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.service.ParametrizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Component
public class CacheConfig {

    private final ParametrizationService parametrizationService;
    private final Map<Integer, List<PaParameterization>> mapParameterization;

    private void loadParameterizationItems(ParameterizationFilter filter) {
        Map<Integer, List<PaParameterization>> map = parametrizationService.getByIdClient(filter, true);
        mapParameterization.clear();
        mapParameterization.putAll(map);
    }

    public List<PaParameterization>  getParameterizationsByClient(int idClient,ParameterizationFilter filter) {
        if (mapParameterization.isEmpty())
            loadParameterizationItems(filter);
        return mapParameterization.getOrDefault(idClient, null);
    }
}
