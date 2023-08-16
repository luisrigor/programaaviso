package com.gsc.programaavisos.service.impl;

import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.dto.ParameterizationDTO;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.repository.crm.impl.ItemsModelRepository;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ParametrizationService;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.utils.DataBaseTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

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
    private final ItemsDealerRepository itemsDealerRepository;
    private final ItemsModelRepository itemsModelRepository;
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
                    //parameterizationItem.setItemModels(ItemsModel.getHelper().getModelsByParameterizationItems(idParameterizationItem));
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

    private void saveParametrization(ParameterizationDTO parameterizationDTO, UserPrincipal oGSCUser) {
        /*
         if(request.getParameter(PARAMETRIZATION_JSON)!=null){
                jsonObjStr = request.getParameter(PARAMETRIZATION_JSON);
        }*/
        PaParameterization oParameterization = PaParameterization.builder()
                .id(parameterizationDTO.getId())
                .dtStart(parameterizationDTO.getDtStart())
                .dtEnd(parameterizationDTO.getDtEnd())
                .comments(parameterizationDTO.getComments())
                .published(parameterizationDTO.getPublished())
                .visible(parameterizationDTO.getVisible())
                .type(parameterizationDTO.getType())
                .parameterizationItems(parameterizationDTO.getParametrizationItems())
                .idBrand(ApiConstants.getIdBrand(oGSCUser.getOidNet()))
                .createdBy("oGSCUser.getUserStamp()")
                .build();

        insertParametrization(oParameterization.getId() > 0, oParameterization, oGSCUser);
    }

    private void insertParametrization(Boolean isNotNew, PaParameterization oParameterization, UserPrincipal oGSCUser) {
        try {
            if (isNotNew)
                parametrizationItemsRepository.deleteById(oParameterization.getId());

            paParameterizationRepository.save(oParameterization);
            String createdBy = "oGSCUser.getUserStamp()";

            for (ParametrizationItems parameterizationItem : oParameterization.getParameterizationItems()) {
                if (parameterizationItem!=null){
                    parameterizationItem.setIdParameterization(oParameterization.getId());
                    parametrizationItemsRepository.save(parameterizationItem);
                    int parameterizationItemId = parameterizationItem.getId();

/*
                    for (ItemsAge age : parameterizationItem.getItemAges()) {
                        age.setIdParameterizationItems(parameterizationItemId);
                        age.setCreatedBy(createdBy);
                        itemsAgeRepository.save(age);
                    }
                    for (ItemsKilometers kilometer : parameterizationItem.getItemKilometers()) {
                        kilometer.setIdParameterizationItems(parameterizationItemId);
                        kilometer.setCreatedBy(createdBy);
                        itemsKilometersRepository.save(kilometer);
                    }
                    for (ItemsFidelitys fidelity : parameterizationItem.getItemFidelitys()) {
                        fidelity.setIdParameterizationItems(parameterizationItemId);
                        fidelity.setCreatedBy(createdBy);
                        itemsFidelitysRepository.save(fidelity);
                    }
                    for (ItemsFuel fuel : parameterizationItem.getItemFuels()) {
                        fuel.setIdParameterizationItems(parameterizationItemId);
                        fuel.setCreatedBy(createdBy);
                        itemsFuelRepository.save(fuel);
                    }
                    for (ItemsDealer dealer : parameterizationItem.getItemDealers()) {
                        dealer.setIdParameterizationItems(parameterizationItemId);
                        dealer.setCreatedBy(createdBy);
                        itemsDealerRepository.save(dealer);
                    }
                    for (ItemsGenre genre : parameterizationItem.getItemGenres()) {
                        genre.setIdParameterizationItems(parameterizationItemId);
                        genre.setCreatedBy(createdBy);
                        itemsGenreRepository.save(genre);                    }
                    for (ItemsModel model : parameterizationItem.getItemModels()) {
                        model.setIdParameterizationItems(parameterizationItemId);
                        model.setCreatedBy(createdBy);
                        itemsModelRepository.save(model);                    }
                    for (ItemsEntityType entityType : parameterizationItem.getItemEntityTypes()) {
                        entityType.setIdParameterizationItems(parameterizationItemId);
                        entityType.setCreatedBy(createdBy);
                        itemsEntityTypeRepository.save(entityType);
                    }
                    /*
                    saveItems(parameterizationItem.getItemAges(), parameterizationItemId, createdBy, ItemsAge::setIdParameterizationItems, ItemsAge::setCreatedBy, itemsAgeRepository);
                    saveItems(parameterizationItem.getItemKilometers(), parameterizationItemId, createdBy, ItemsKilometers::setIdParameterizationItems, ItemsKilometers::setCreatedBy, itemsKilometersRepository);
                    saveItems(parameterizationItem.getItemFidelitys(), parameterizationItemId, createdBy, ItemsFidelitys::setIdParameterizationItems, ItemsFidelitys::setCreatedBy, itemsFidelitysRepository);
                    saveItems(parameterizationItem.getItemFuels(), parameterizationItemId, createdBy, ItemsFuel::setIdParameterizationItems, ItemsFuel::setCreatedBy, itemsFuelRepository);
                    saveItems(parameterizationItem.getItemDealers(), parameterizationItemId, createdBy, ItemsDealer::setIdParameterizationItems, ItemsDealer::setCreatedBy, itemsDealerRepository);
                    saveItems(parameterizationItem.getItemGenres(), parameterizationItemId, createdBy, ItemsGenre::setIdParameterizationItems, ItemsGenre::setCreatedBy, itemsGenreRepository);
                    saveItems(parameterizationItem.getItemModels(), parameterizationItemId, createdBy, ItemsModel::setIdParameterizationItems, ItemsModel::setCreatedBy, itemsModelRepository);
                    saveItems(parameterizationItem.getItemEntityTypes(), parameterizationItemId, createdBy, ItemsEntityType::setIdParameterizationItems, ItemsEntityType::setCreatedBy, itemsEntityTypeRepository);
                     */
                }
            }

        }catch (Exception e){
            throw new ProgramaAvisosException("Error new parametrization", e);
        }

    }

    private <T> void saveItems(List<T> items, Integer parameterizationItemId, String createdBy, BiConsumer<T, Integer> idParamSetter,
                               BiConsumer<T, String> createdBySetter, JpaRepository<T, Integer> repository) {
        for (T item : items) {
            idParamSetter.accept(item, parameterizationItemId);
            createdBySetter.accept(item, createdBy);
            repository.save(item);
        }
    }

}
