package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ManageItemsDTO;
import com.gsc.programaavisos.dto.SaveManageItemDTO;
import com.gsc.programaavisos.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;

public interface ItemService {
    List<DocumentUnitDTO> searchItems(String searchInput, Date startDate, Integer tpaItemType, UserPrincipal userPrincipal);
    ManageItemsDTO getManageItems(UserPrincipal userPrincipal, int itemType, int itemId);
    List<DocumentUnitDTO> getListManagesItems(UserPrincipal userPrincipal,String searchInput,Integer itemType);
    void saveManageItems(UserPrincipal userPrincipal, SaveManageItemDTO saveManageItemDTO, MultipartFile[] files);
}
