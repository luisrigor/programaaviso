package com.gsc.programaavisos.service.impl;


import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.constants.PaConstants;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ItemFilter;
import com.gsc.programaavisos.dto.ManageItemsDTO;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.DocumentUnit;
import com.gsc.programaavisos.model.crm.entity.DocumentUnitCategory;
import com.gsc.programaavisos.repository.crm.DocumentUnitCategoryRepository;
import com.gsc.programaavisos.repository.crm.DocumentUnitRepository;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.ItemService;
import com.sc.commons.utils.SftpTasks;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import static com.gsc.programaavisos.config.environment.MapProfileVariables.*;

@Service
@Log4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final DocumentUnitRepository documentUnitRepository;
    private final DocumentUnitCategoryRepository documentUnitCategoryRepository;
    private final EnvironmentConfig environmentConfig;


    @Override
    public List<DocumentUnitDTO> searchItems(String searchInput,Date endDate,Integer tpaItemType, UserPrincipal userPrincipal) {
        try {
            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());
            ItemFilter filter = ItemFilter.builder()
                    .searchInput(searchInput)
                    .itemType(tpaItemType)
                    .dtEnd(endDate)
                    .idBrand(idBrand)
                    .build();
            return  documentUnitRepository.getByFilter(filter);
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching search items ", e);
        }
    }

    @Override
    public ManageItemsDTO getManageItems(UserPrincipal userPrincipal, int itemType, int itemId) {

        DocumentUnit item = null;
        List<DocumentUnitCategory> categories;
        Map<String, String> envV = environmentConfig.getEnvVariables();
        try {
            categories = documentUnitCategoryRepository.getByType(itemType);
            if (itemId > 0) {
                item = documentUnitRepository.findById(itemId).orElseThrow(()-> new ProgramaAvisosException("itemId not found " + itemId));

                String ePostal = item.getImgEPostal();
                String postal = item.getImgPostal();

                String uplodadDir = System.getProperty("java.io.tmpdir");
                if (itemType == PaConstants.PARAM_ID_TPA_ITEM_TYPE_SERVICE) {

                    if (ePostal != null) {
                        SftpTasks.getFile(envV.get(CONST_FTP_MANAGE_ITEM_SERVER),
                                envV.get(CONST_FTP_MANAGE_ITEM_LOGIN), envV.get(CONST_FTP_MANAGE_ITEM_PWD), ePostal,
                                false, envV.get(CONST_FTP_MANAGE_ITEM_ADDRESS)  + PaConstants.FTP_EPOSTAL_PATH, uplodadDir);
                    }

                    if (postal != null) {
                        SftpTasks.getFile(envV.get(CONST_FTP_MANAGE_ITEM_SERVER),
                                envV.get(CONST_FTP_MANAGE_ITEM_LOGIN), envV.get(CONST_FTP_MANAGE_ITEM_PWD), postal,
                                false, envV.get(CONST_FTP_MANAGE_ITEM_ADDRESS) + PaConstants.FTP_POSTAL_SERVICE, uplodadDir);
                    }

                } else if (itemType == PaConstants.PARAM_ID_TPA_ITEM_TYPE_HIGHLIGHT) {

                    if (ePostal != null) {
                        SftpTasks.getFile(envV.get(CONST_FTP_MANAGE_ITEM_SERVER),
                                envV.get(CONST_FTP_MANAGE_ITEM_LOGIN), envV.get(CONST_FTP_MANAGE_ITEM_PWD), ePostal,
                                false, envV.get(CONST_FTP_MANAGE_ITEM_ADDRESS)  + PaConstants.FTP_EPOSTAL_PATH, uplodadDir);
                    }

                    if (postal != null) {
                        SftpTasks.getFile(envV.get(CONST_FTP_MANAGE_ITEM_SERVER),
                                envV.get(CONST_FTP_MANAGE_ITEM_LOGIN), envV.get(CONST_FTP_MANAGE_ITEM_PWD), postal,
                                false, envV.get(CONST_FTP_MANAGE_ITEM_ADDRESS)  + PaConstants.FTP_POSTAL_DESTAQUE, uplodadDir);
                    }

                } else if (itemType == PaConstants.PARAM_ID_TPA_ITEM_TYPE_HEADER) {

                    if (ePostal != null) {
                        SftpTasks.getFile(envV.get(CONST_FTP_MANAGE_ITEM_SERVER),
                                envV.get(CONST_FTP_MANAGE_ITEM_LOGIN), envV.get(CONST_FTP_MANAGE_ITEM_PWD), ePostal,
                                false, envV.get(CONST_FTP_MANAGE_ITEM_ADDRESS) + PaConstants.FTP_EPOSTAL_PATH, uplodadDir);
                    }

                    if (postal != null) {
                        SftpTasks.getFile(envV.get(CONST_FTP_MANAGE_ITEM_SERVER),
                                envV.get(CONST_FTP_MANAGE_ITEM_LOGIN), envV.get(CONST_FTP_MANAGE_ITEM_PWD), postal,
                                false, envV.get(CONST_FTP_MANAGE_ITEM_ADDRESS) + PaConstants.FTP_POSTAL_HEADER, uplodadDir);
                    }

                }

            }
            return ManageItemsDTO.builder()
                    .item(item)
                    .itemType(itemType)
                    .categories(categories)
                    .itemId(item!=null?item.getId():null)
                    .build();
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error fetching manage items ", e);
        }
    }

    @Override
    public List<DocumentUnitDTO> getListManagesItems(UserPrincipal userPrincipal,String searchInput,Integer itemType) {
        try{
            int idBrand = ApiConstants.getIdBrand(userPrincipal.getOidNet());
            ItemFilter oItemFilter = ItemFilter.builder().searchInput(searchInput).itemType(itemType).idBrand(idBrand).build();
            return documentUnitRepository.getByFilter(oItemFilter);
        }catch(Exception e){
            throw new ProgramaAvisosException("Error fetching manage items list ", e);
        }
    }
}
