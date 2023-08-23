package com.gsc.programaavisos.service.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gsc.programaavisos.exceptions.ObjectMappingException;
import com.gsc.programaavisos.model.crm.PaDataInfoP;
import com.gsc.programaavisos.model.crm.entity.PaDataInfo;
import lombok.extern.log4j.Log4j;

@Log4j
public class PaDataInfoConverter {

    static ObjectMapper objectMapper = new ObjectMapper();

    public PaDataInfoConverter(){}

    public static PaDataInfoP paDataInfoToP(PaDataInfo paDataInfo) {
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.convertValue(paDataInfo, PaDataInfoP.class);
        } catch (ObjectMappingException ex) {
            throw new ObjectMappingException("Error Mapping from dataInfo To model{} ", ex);
        }
    }
}
