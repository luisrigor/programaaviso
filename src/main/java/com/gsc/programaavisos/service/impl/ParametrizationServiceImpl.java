package com.gsc.programaavisos.service.impl;

import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.dto.ParameterizationFilter;
import com.gsc.programaavisos.dto.ParameterizationDTO;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.*;
import com.gsc.programaavisos.repository.crm.*;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ParametrizationService;
import com.gsc.programaavisos.util.PAUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDateTime;
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
            PaParameterization parameterization = paParameterizationRepository.findById(idParametrization).orElseThrow(()-> new ProgramaAvisosException("Id not found: " + idParametrization));
            List<ParametrizationItems> parametrizationItems = getParameterizationItemsByParameterizationId(idParametrization, onlyActives);
            if (!parametrizationItems.isEmpty()) {
                parameterization.setParametrizationItems(parametrizationItems);
                for (ParametrizationItems parameterizationItem : parameterization.getParametrizationItems()) {
                    Integer idParameterizationItem = parameterizationItem.getId();
                    parameterizationItem.setItemEntityTypes(itemsEntityTypeRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemAges(itemsAgeRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemKilometers(itemsKilometersRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemFidelitys(itemsFidelitysRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemFuels(itemsFuelRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemGenres(itemsGenreRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemDealers(itemsDealerRepository.findByIdParameterizationItems(idParameterizationItem));
                    parameterizationItem.setItemModels(itemsModelRepository.findByIdParameterizationItems(idParameterizationItem));
                }
            }
            return parameterization;
        }catch (Exception e){
            log.error("Error new parametrization");
            throw new ProgramaAvisosException("Error new parametrization -->", e);
        }
    }

    private List<ParametrizationItems> getParameterizationItemsByParameterizationId(Integer idParametrization, boolean onlyActives){
        if(onlyActives){
            return parametrizationItemsRepository.getAllParametrizationItemOnlyActive(idParametrization);
        }else{
            return parametrizationItemsRepository.getAllParametrizationItem(idParametrization);
        }
    }

    @Override
    public void saveParameterization(UserPrincipal oGSCUser, ParameterizationDTO parameterizationDTO) {
        PaParameterization oParameterization = PaParameterization.builder()
                .id(parameterizationDTO.getId())
                .dtStart(new java.sql.Date(parameterizationDTO.getDtStart().getTime()))
                .dtEnd(new java.sql.Date(parameterizationDTO.getDtEnd().getTime()))
                .comments(parameterizationDTO.getComments())
                .published(parameterizationDTO.getPublished())
                .visible(parameterizationDTO.getVisible())
                .type(parameterizationDTO.getType())
                .parametrizationItems(parameterizationDTO.getParametrizationItems())
                .idBrand(ApiConstants.getIdBrand(oGSCUser.getOidNet()))
                .createdBy(PAUtil.getUserStamp(oGSCUser.getUsername()))
                .dtCreated(LocalDateTime.now())
                .build();
        insertParametrization(oParameterization.getId() > 0, oParameterization, oGSCUser);
    }

    public void insertParametrization(boolean isNotNew, PaParameterization oParameterization, UserPrincipal oGSCUser ) {
        try {
            if (isNotNew)
                parametrizationItemsRepository.deleteById(oParameterization.getId());
            PaParameterization insertedParam = paParameterizationRepository.save(oParameterization);
            String createdBy = PAUtil.getUserStamp(oGSCUser.getUsername());
            for (ParametrizationItems parameterizationItem : oParameterization.getParametrizationItems()) {
                if (parameterizationItem!=null){

                    parameterizationItem.setIdParameterization(insertedParam.getId());
                    parameterizationItem.setDtCreated(LocalDateTime.now());
                    parameterizationItem.setCreatedBy(createdBy);
                    parametrizationItemsRepository.save(parameterizationItem);
                    int parameterizationItemId = parameterizationItem.getId();

                    saveItemsList(parameterizationItem.getItemAges(), parameterizationItemId, createdBy, ItemsAge::setIdParameterizationItems, ItemsAge::setCreatedBy, ItemsAge::setDtCreated, itemsAgeRepository);
                    saveItemsList(parameterizationItem.getItemKilometers(), parameterizationItemId, createdBy, ItemsKilometers::setIdParameterizationItems, ItemsKilometers::setCreatedBy, ItemsKilometers::setDtCreated, itemsKilometersRepository);
                    saveItemsList(parameterizationItem.getItemFidelitys(), parameterizationItemId, createdBy, ItemsFidelitys::setIdParameterizationItems, ItemsFidelitys::setCreatedBy, ItemsFidelitys::setDtCreated, itemsFidelitysRepository);
                    saveItemsList(parameterizationItem.getItemFuels(), parameterizationItemId, createdBy, ItemsFuel::setIdParameterizationItems, ItemsFuel::setCreatedBy, ItemsFuel::setDtCreated, itemsFuelRepository);
                    saveItemsList(parameterizationItem.getItemDealers(), parameterizationItemId, createdBy, ItemsDealer::setIdParameterizationItems, ItemsDealer::setCreatedBy, ItemsDealer::setDtCreated, itemsDealerRepository);
                    saveItemsList(parameterizationItem.getItemGenres(), parameterizationItemId, createdBy, ItemsGenre::setIdParameterizationItems, ItemsGenre::setCreatedBy, ItemsGenre::setDtCreated, itemsGenreRepository);
                    saveItemsList(parameterizationItem.getItemModels(), parameterizationItemId, createdBy, ItemsModel::setIdParameterizationItems, ItemsModel::setCreatedBy, ItemsModel::setDtCreated, itemsModelRepository);
                    saveItemsList(parameterizationItem.getItemEntityTypes(), parameterizationItemId, createdBy, ItemsEntityType::setIdParameterizationItems, ItemsEntityType::setCreatedBy, ItemsEntityType::setDtCreated, itemsEntityTypeRepository);
                }
            }
        }catch (Exception e){
            throw new ProgramaAvisosException("Error insert parametrization", e);
        }
    }

    public  <T> void saveItemsList(List<T> items, Integer parameterizationItemId, String createdBy, BiConsumer<T, Integer> idParamSetter,
                                   BiConsumer<T, String> createdBySetter, BiConsumer<T, LocalDateTime> dtCreatedSetter, JpaRepository<T, Integer> repository) {
        for (T item : items) {
            idParamSetter.accept(item, parameterizationItemId);
            createdBySetter.accept(item, createdBy);
            dtCreatedSetter.accept(item, LocalDateTime.now());
            repository.save(item);
        }
    }

    @Override
    public void cloneParameterization(UserPrincipal oGSCUser, Integer id) {
        try {
            PaParameterization paramToClone = getById(id, true);
            PaParameterization cloneParameterization = PaParameterization.builder()
                    .id(0)
                    .dtStart(paramToClone.getDtStart())
                    .dtEnd(paramToClone.getDtEnd())
                    .comments(paramToClone.getComments())
                    .published(paramToClone.getPublished())
                    .visible(paramToClone.getVisible())
                    .type(paramToClone.getType())
                    .idBrand(paramToClone.getIdBrand())
                    .createdBy(PAUtil.getUserStamp(oGSCUser.getUsername()))
                    .dtCreated(LocalDateTime.now())
                    .parametrizationItems(paramToClone.getParametrizationItems())
                    .build();
            cloneItemList(cloneParameterization,oGSCUser);
        }catch (Exception e){
            throw new ProgramaAvisosException("Error clone parametrization", e);
        }
    }

    public void cloneItemList(PaParameterization oParameterization, UserPrincipal oGSCUser) {
        try {
            PaParameterization insertedParam = paParameterizationRepository.save(oParameterization);
            String createdBy = PAUtil.getUserStamp(oGSCUser.getUsername());
            for (ParametrizationItems paramToClone : oParameterization.getParametrizationItems()) {
                if (paramToClone!=null){

                    ParametrizationItems cloneParaItem = paramToClone.toBuilder()
                            .id(0)
                            .idParameterization(insertedParam.getId())
                            .createdBy(createdBy)
                            .dtCreated(LocalDateTime.now())
                            .build();

                    int parameterizationItemId = parametrizationItemsRepository.save(cloneParaItem).getId();

                    for (ItemsAge age:paramToClone.getItemAges()){
                        ItemsAge clone = new ItemsAge(null,parameterizationItemId,age.getIdAge(),createdBy,LocalDateTime.now());
                        itemsAgeRepository.save(clone);
                    }
                    for (ItemsKilometers kilometers:paramToClone.getItemKilometers()){
                        ItemsKilometers clone = new ItemsKilometers(null,parameterizationItemId,kilometers.getIdKilometers(),createdBy,LocalDateTime.now());
                        itemsKilometersRepository.save(clone);
                    }
                    for (ItemsFidelitys itemsFidelitys:paramToClone.getItemFidelitys()){
                        ItemsFidelitys clone = new ItemsFidelitys(null,parameterizationItemId,itemsFidelitys.getIdFidelity(),createdBy,LocalDateTime.now());
                        itemsFidelitysRepository.save(clone);
                    }
                    for (ItemsFuel fuel:paramToClone.getItemFuels()){
                        ItemsFuel clone = new ItemsFuel(null,parameterizationItemId,fuel.getIdFuel(),createdBy,LocalDateTime.now());
                        itemsFuelRepository.save(clone);
                    }
                    for (ItemsDealer dealer:paramToClone.getItemDealers()){
                        ItemsDealer clone = new ItemsDealer(null,parameterizationItemId,dealer.getIdDealer(),createdBy,LocalDateTime.now());
                        itemsDealerRepository.save(clone);
                    }
                    for (ItemsGenre genre:paramToClone.getItemGenres()){
                        ItemsGenre clone = new ItemsGenre(null,parameterizationItemId,genre.getIdGenre(),createdBy,LocalDateTime.now());
                        itemsGenreRepository.save(clone);
                    }
                    for (ItemsEntityType entityType:paramToClone.getItemEntityTypes()){
                        ItemsEntityType clone = new ItemsEntityType(null,parameterizationItemId,entityType.getIdEnittyType(),createdBy,LocalDateTime.now());
                        itemsEntityTypeRepository.save(clone);
                    }
                    for (ItemsModel model:paramToClone.getItemModels()){
                        ItemsModel clone = new ItemsModel(null,parameterizationItemId,model.getIdGamma(),createdBy,LocalDateTime.now());
                        itemsModelRepository.save(clone);
                    }
                }
            }
        }catch (Exception e){
            throw new ProgramaAvisosException("Error clone parametrization", e);
        }
    }

}
