package com.gsc.programaavisos.service.impl;

import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.ItemsKilometers;
import com.gsc.programaavisos.model.crm.entity.PaParameterization;
import com.gsc.programaavisos.model.crm.entity.ParametrizationItems;
import com.gsc.programaavisos.repository.crm.*;
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
    private final ItemsFuelRepository itemsFuelRepository;
    private final ItemsGenreRepository  itemsGenreRepository;
    private final ItemsAgeRepository itemsAgeRepository;
    private final ItemsEntityTypeRepository itemsEntityTypeRepository;
    private final ItemsFidelitysRepository itemsFidelitysRepository;
    private final ItemsKilometersRepository itemsKilometersRepository;
    private final ParametrizationItemsRepository parametrizationItemsRepository;
    @Override
    public List<PaParameterization> searchParametrization(Date startDate, Date endDate, String selectedTypeParam, UserPrincipal userPrincipal) {
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

    @Override
    public void deleteParametrization(UserPrincipal userPrincipal, Integer id) {
        log.info("deleteParametrization service: " + id);
        try{
            paParameterizationRepository.deleteById(id);
        } catch (Exception e) {
            log.error("Error delete parametrization");
            throw new ProgramaAvisosException("Error delete parametrization", e);
        }
    }

    @Override
    public List<PaParameterization> getParametrizationsList(UserPrincipal userPrincipal) {
        try {
            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());
            log.debug("idBrand >>" + idBrand);
            ParameterizationFilter oParameterizationFilter = ParameterizationFilter.builder().idBrand(idBrand).build();
           return paParameterizationRepository.getByFilter(oParameterizationFilter);
        } catch (Exception e) {
            log.error("Error parametrization list");
            throw new ProgramaAvisosException("Error parametrization list", e);
        }
    }

    @Override
    public PaParameterization getNewParametrization(UserPrincipal userPrincipal,Integer id) {
       return getById(id,false);
    }

    public PaParameterization getById(int idParametrization, boolean onlyActives){
        try {
            PaParameterization parameterization = paParameterizationRepository.findById(idParametrization).get();
            List<ParametrizationItems> parameterizationItems = getParameterizationItemsByParameterizationId(idParametrization, onlyActives);
            if (!parameterizationItems.isEmpty()) {
                parameterization.setParameterizationItems(parameterizationItems);
                for (ParametrizationItems parameterizationItem : parameterization.getParameterizationItems()) {
                    Integer idParameterizationItem = parameterizationItem.getId();
                    parameterizationItem.setItemEntityTypes(itemsEntityTypeRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemAges(itemsAgeRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemKilometers(itemsKilometersRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemFidelitys(itemsFidelitysRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemFuels(itemsFuelRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemGenres(itemsGenreRepository.findByIdParameterizationItems(idParameterizationItem));
                    //parameterizationItem.setItemDealers(ItemsDealer.getHelper().getDealersByParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemModels(ItemsModel.getHelper().getModelsByParameterizationItems(idParameterizationItem));
                }
            }
            return parameterization;
        }catch (Exception e){
            log.error("Error new parametrization");
            throw new ProgramaAvisosException("Error new parametrization", e);
        }
    }

    private List<ParametrizationItems> getParameterizationItemsByParameterizationId(Integer idParametrization,boolean onlyActives){
        if(onlyActives){
            return parametrizationItemsRepository.getAllParametrizationItemOnlyActive(idParametrization);
        }else{
            return parametrizationItemsRepository.getAllParametrizationItem(idParametrization);
        }
    }
}
