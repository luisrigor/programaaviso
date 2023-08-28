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
import com.gsc.programaavisos.util.PAUtil;
import com.sc.commons.dbconnection.ServerJDBCConnection;
import com.sc.commons.exceptions.SCErrorException;
import com.sc.commons.user.GSCUser;
import com.sc.commons.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
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
    public void saveManageItems(UserPrincipal oGSCUser, SaveManageItemDTO saveManageItemDTO) {

        int idBrand = ApiConstants.getIdBrand(oGSCUser.getOidNet());

        String uplodadDir = System.getProperty("java.io.tmpdir");

        File fileAttach1;
        File fileAttach2;

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

                String fiel1dInputName = "img1";
                String fiel2dInputName = "img2";

                /**
                 *
                 *  // PortletMultipartWrapper portletMpWrapper = new PortletMultipartWrapper(request, 0, MAXFILESIZE,
                 *             // MAXFILESIZE,uplodadDir);
                 */
                FileItem file1AttachItem = PAUtil.getFileItem(fiel1dInputName);
                FileItem file2AttachItem = PAUtil.getFileItem(fiel2dInputName);

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

                if (file1AttachItem != null) {
                    BufferedImage image = ImageIO.read(file1AttachItem.getInputStream());
                    extension = file1AttachItem.getName().substring(file1AttachItem.getName().indexOf(".") + 1,
                            file1AttachItem.getName().length());
                    documentUnit.setImgPostal(code + "." + extension);
                    fileAttach1 = new File(uplodadDir + PaConstants.BACKSLASH + code + "." + extension);
                    ImageIO.write(image, extension, fileAttach1);

                    if (idItemType == 1) {
                        SftpTasks.putFile(PaConstants.FTP_MANAGE_ITEM_SERVER, PaConstants.FTP_MANAGE_ITEM_LOGIN,
                                PaConstants.FTP_MANAGE_ITEM_PWD, fileAttach1,
                                PaConstants.FTP_MANAGE_ITEM_ADDRESS + PaConstants.FTP_POSTAL_SERVICE);
                    } else if (idItemType == 2) {
                        SftpTasks.putFile(PaConstants.FTP_MANAGE_ITEM_SERVER, PaConstants.FTP_MANAGE_ITEM_LOGIN,
                                PaConstants.FTP_MANAGE_ITEM_PWD, fileAttach1,
                                PaConstants.FTP_MANAGE_ITEM_ADDRESS + PaConstants.FTP_POSTAL_DESTAQUE);
                    } else if (idItemType == 3) {
                        SftpTasks.putFile(PaConstants.FTP_MANAGE_ITEM_SERVER, PaConstants.FTP_MANAGE_ITEM_LOGIN,
                                PaConstants.FTP_MANAGE_ITEM_PWD, fileAttach1,
                                PaConstants.FTP_MANAGE_ITEM_ADDRESS + PaConstants.FTP_POSTAL_HEADER);
                    }
                }
                if (file2AttachItem != null) {
                    BufferedImage image = ImageIO.read(file2AttachItem.getInputStream());
                    extension = file2AttachItem.getName().substring(file2AttachItem.getName().indexOf(".") + 1,
                            file2AttachItem.getName().length());
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


}
