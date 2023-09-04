package com.gsc.programaavisos.service.impl;


import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.constants.ApiConstants;
import com.gsc.programaavisos.constants.PaConstants;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ItemFilter;
import com.gsc.programaavisos.dto.ManageItemsDTO;
import com.gsc.programaavisos.dto.SaveManageItemDTO;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gsc.programaavisos.config.environment.MapProfileVariables.*;
import static com.gsc.programaavisos.config.environment.MapProfileVariables.CONST_FTP_MANAGE_ITEM_ADDRESS;

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

    @Override
    public void saveManageItems(UserPrincipal oGSCUser, SaveManageItemDTO saveManageItemDTO, MultipartFile[] files) {

        int idBrand = ApiConstants.getIdBrand(oGSCUser.getOidNet());

        String uplodadDir = System.getProperty("java.io.tmpdir");

        File fileAttach1;
        File fileAttach2;

        Map<String, String> envVar = environmentConfig.getEnvVariables();


        try {
            // PortletMultipartWrapper portletMpWrapper = new PortletMultipartWrapper(request, 0, MAXFILESIZE,
            // MAXFILESIZE,uplodadDir);
            Integer idItemType = saveManageItemDTO.getIdItemType();
            String name = StringTasks.cleanString(saveManageItemDTO.getServiceName(), StringUtils.EMPTY);
            String code = StringTasks.cleanString(saveManageItemDTO.getServiceCode(), StringUtils.EMPTY);
            String link = StringTasks.cleanString(saveManageItemDTO.getServiceLink(), StringUtils.EMPTY);
            Integer categoryId = saveManageItemDTO.getCategory();
            Integer id = StringTasks.cleanInteger(saveManageItemDTO.getIdTpaItem().toString(), 0);

            LocalDate dtEnd = saveManageItemDTO.getEndDateInput() == null ? LocalDate.now() : saveManageItemDTO.getEndDateInput();

            String description = StringTasks.cleanString(saveManageItemDTO.getDescription(), StringUtils.EMPTY)
                    .replaceAll("[\\t\\n\\r]+", StringUtils.EMPTY);

            DocumentUnit documentUnit;

            if (id > 0) {
                documentUnit = documentUnitRepository.findById(id).orElseThrow(() -> new ProgramaAvisosException("itemId not found " + id));
                documentUnit.setIdDocumentUnitCategory(categoryId);
                documentUnit.setLink(link);
                documentUnit.setName(name);
                documentUnit.setDescription(description);
                documentUnit.setDtEnd(dtEnd);
                documentUnitRepository.save(documentUnit);
            } else {
                List<MultipartFile> fileAttachItems = new ArrayList<>();

                for (MultipartFile currentFile : files) {
                    fileAttachItems.add(currentFile);
                }

                documentUnit = new DocumentUnit();
                documentUnit.setStatus("S");
                documentUnit.setIdDocumentUnitType(idItemType);
                documentUnit.setIdDocumentUnitCategory(categoryId);
                documentUnit.setLink(link);
                documentUnit.setCode(code);
                documentUnit.setName(name);
                documentUnit.setDescription(description);
                documentUnit.setIdBrand(idBrand);
                documentUnit.setDtEnd(dtEnd);

                String extension;

                if (fileAttachItems.size()>0) {
                    BufferedImage image = ImageIO.read(fileAttachItems.get(0).getInputStream());
                    extension = getFileExtension(fileAttachItems.get(0));
                    documentUnit.setImgPostal(code + "." + extension);
                    fileAttach1 = new File(uplodadDir + PaConstants.BACKSLASH + code + "." + extension);
                    ImageIO.write(image, extension, fileAttach1);

                    if (idItemType == 1) {
                        SftpTasks.putFile(envVar.get(CONST_FTP_MANAGE_ITEM_SERVER), envVar.get(CONST_FTP_MANAGE_ITEM_LOGIN),
                                envVar.get(CONST_FTP_MANAGE_ITEM_PWD), fileAttach1,
                                        envVar.get(CONST_FTP_MANAGE_ITEM_ADDRESS) + PaConstants.FTP_POSTAL_SERVICE);
                    } else if (idItemType == 2) {
                        SftpTasks.putFile(envVar.get(CONST_FTP_MANAGE_ITEM_SERVER), envVar.get(CONST_FTP_MANAGE_ITEM_LOGIN),
                                envVar.get(CONST_FTP_MANAGE_ITEM_PWD), fileAttach1,
                                        envVar.get(CONST_FTP_MANAGE_ITEM_ADDRESS) + PaConstants.FTP_POSTAL_DESTAQUE);
                    } else if (idItemType == 3) {
                        SftpTasks.putFile(envVar.get(CONST_FTP_MANAGE_ITEM_SERVER), envVar.get(CONST_FTP_MANAGE_ITEM_LOGIN),
                                envVar.get(CONST_FTP_MANAGE_ITEM_PWD), fileAttach1,
                                        envVar.get(CONST_FTP_MANAGE_ITEM_ADDRESS) + PaConstants.FTP_POSTAL_HEADER);
                    }
                }
                if (fileAttachItems.size()>1) {
                    BufferedImage image = ImageIO.read(fileAttachItems.get(1).getInputStream());
                    extension = getFileExtension(fileAttachItems.get(1));
                    documentUnit.setImgEPostal(code + "." + extension);
                    fileAttach2 = new File(uplodadDir + "/" + code + "." + extension);
                    ImageIO.write(image, extension, fileAttach2);

                    SftpTasks.putFile(PaConstants.FTP_MANAGE_ITEM_SERVER, PaConstants.FTP_MANAGE_ITEM_LOGIN,
                            PaConstants.FTP_MANAGE_ITEM_PWD, fileAttach2,
                            PaConstants.FTP_MANAGE_ITEM_ADDRESS + PaConstants.FTP_EPOSTAL_PATH);
                }
                documentUnitRepository.save(documentUnit);
            }
        } catch (Exception e) {
            throw new ProgramaAvisosException("Error saving manage items ", e);
        }
    }

    public String getFileExtension(MultipartFile file) {
        String originalFileName = file.getOriginalFilename();
        if (org.springframework.util.StringUtils.hasText(originalFileName)) {
            int dotIndex = originalFileName.lastIndexOf('.');
            if (dotIndex >= 0) {
                return originalFileName.substring(dotIndex + 1).toLowerCase();
            }
        }
        return null;
    }


}
