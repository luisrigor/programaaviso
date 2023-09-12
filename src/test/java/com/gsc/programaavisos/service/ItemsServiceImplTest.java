package com.gsc.programaavisos.service;

import com.gsc.programaavisos.config.environment.EnvironmentConfig;
import com.gsc.programaavisos.dto.DocumentUnitDTO;
import com.gsc.programaavisos.dto.ManageItemsDTO;
import com.gsc.programaavisos.dto.SaveManageItemDTO;
import com.gsc.programaavisos.exceptions.ProgramaAvisosException;
import com.gsc.programaavisos.model.crm.entity.DocumentUnit;
import com.gsc.programaavisos.model.crm.entity.DocumentUnitCategory;
import com.gsc.programaavisos.repository.crm.DocumentUnitCategoryRepository;
import com.gsc.programaavisos.repository.crm.DocumentUnitRepository;
import com.gsc.programaavisos.sample.data.provider.ItemData;
import com.gsc.programaavisos.sample.data.provider.OtherFlowData;
import com.gsc.programaavisos.sample.data.provider.SecurityData;
import com.gsc.programaavisos.security.UserPrincipal;
import com.gsc.programaavisos.service.impl.ItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ActiveProfiles(SecurityData.ACTIVE_PROFILE)
class ItemsServiceImplTest {

    @Mock
    private DocumentUnitRepository documentUnitRepository;
    @Mock
    private DocumentUnitCategoryRepository documentUnitCategoryRepository;
    @Mock
    private EnvironmentConfig environmentConfig;
    @InjectMocks
    private ItemServiceImpl itemService;

    private SecurityData securityData;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityData = new SecurityData();
    }

    @Test
    void  whenSearchItemsListThenReturnSuccessfully() {
        // Arrange
        List<DocumentUnitDTO> expectedDocumentList = new ArrayList<>(Collections.singletonList(OtherFlowData.getDocumentUnit()));
        when(documentUnitRepository.getByFilter(any())).thenReturn(expectedDocumentList);
        // Act
        List<DocumentUnitDTO> actualDocumentList = itemService.searchItems(ItemData.RANDOM_NAME,
                Date.valueOf(LocalDate.MIN),ItemData.RANDOM_ID,SecurityData.getUserDefaultStatic());
        // Assert
        Assertions.assertEquals(expectedDocumentList,actualDocumentList);
    }

    @Test
    void whenSearchItemsListAndItemFilterNotFoundThenThrowProgramaAvisosException() {
        //Arrange
        when(documentUnitRepository.getByFilter(any())).thenThrow(ProgramaAvisosException.class);

        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, () -> itemService.searchItems(ItemData.RANDOM_NAME,
                Date.valueOf(LocalDate.MIN), ItemData.RANDOM_ID, SecurityData.getUserDefaultStatic()));
    }

    @Test
    void whenSearchManageItemsListAndItemIdIsMinorThanZeroThenReturnManagedItemsDto() {
        //Arrange
        int itemType = 1;
        int itemId = 0;
        List<DocumentUnitCategory> categories = new ArrayList<>();
        when(documentUnitCategoryRepository.getByType(any())).thenReturn(categories);
        // Act
        ManageItemsDTO actualManageItemsDTO = itemService.getManageItems(SecurityData.getUserDefaultStatic(),itemType,itemId);
        // Assert
        Assertions.assertNull(actualManageItemsDTO.getItem());
        Assertions.assertNull(actualManageItemsDTO.getItemId());
        Assertions.assertEquals(itemType,actualManageItemsDTO.getItemType());
        Assertions.assertEquals(categories,actualManageItemsDTO.getCategories());
    }

    @Test
    void whenSearchManageItemsListAndItemTypeNotFoundThenThrowProgramaAvisosException() {
        //Arrange
        int itemType = 1;
        int itemId = 0;
        List<DocumentUnitCategory> categories = new ArrayList<>();
        when(documentUnitCategoryRepository.getByType(any())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, () ->
                itemService.getManageItems(SecurityData.getUserDefaultStatic(),itemType,itemId));
    }

    @Test
    void whenSearchManageItemsListAndItemIdIsEqualOneThenReturnManagedItemsDto() {
        //Arrange
        int itemType = 1;
        int itemId = 1;
        DocumentUnit expectedItem = new DocumentUnit();
        List<DocumentUnitCategory> categories = new ArrayList<>();
        when(documentUnitCategoryRepository.getByType(any())).thenReturn(categories);
        when(documentUnitRepository.findById(itemId)).thenReturn(Optional.of(expectedItem));
        // Act
        ManageItemsDTO actualManageItemsDTO = itemService.getManageItems(SecurityData.getUserDefaultStatic(),itemType,itemId);
        // Assert
        Assertions.assertEquals(expectedItem,actualManageItemsDTO.getItem());
        Assertions.assertNull(actualManageItemsDTO.getItemId());
        Assertions.assertEquals(itemType,actualManageItemsDTO.getItemType());
        Assertions.assertEquals(categories,actualManageItemsDTO.getCategories());
    }

    @Test
    void whenSearchManageItemsListAndItemIdNotFoundThenThrowProgramaAvisosExceptio() {
        //Arrange
        int itemType = 1;
        int itemId = 1;
        List<DocumentUnitCategory> categories = new ArrayList<>();
        when(documentUnitCategoryRepository.getByType(any())).thenReturn(categories);
        when(documentUnitRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, () ->
                itemService.getManageItems(SecurityData.getUserDefaultStatic(),itemType,itemId));
    }

    @Test
    void whenGetListManagesItemsThenReturnSuccessfully() {
        //Arrange
        UserPrincipal userPrincipal = SecurityData.getUserDefaultStatic();
        List<DocumentUnitDTO> expectedList = new ArrayList<>();
        when(documentUnitRepository.getByFilter(any())).thenReturn(expectedList);
        // Act
        List<DocumentUnitDTO> actualList = itemService.getListManagesItems(userPrincipal,"searchInput",1);
        // Assert
        Assertions.assertEquals(expectedList,actualList);
    }

    @Test
    void whenGetListManagesItemsThenThrowProgramaAvisosExceptio() {
        //Arrange
        UserPrincipal userPrincipal = SecurityData.getUserDefaultStatic();
        when(documentUnitRepository.getByFilter(any())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, () ->
                itemService.getListManagesItems(userPrincipal,"searchInput",1));
    }

    @Test
    void whenSaveManageItemsThenItsSuccessfully() {
        //Arrange
        UserPrincipal userPrincipal = SecurityData.getUserDefaultStatic();
        SaveManageItemDTO saveManageItemDTO = ItemData.getSaveManageItemDto();
        DocumentUnit documentUnit = ItemData.getDocumentUnit();
        when(documentUnitRepository.findById(anyInt())).thenReturn(Optional.ofNullable(documentUnit));
        when(documentUnitRepository.findById(anyInt())).thenReturn(Optional.ofNullable(documentUnit));
        // Act
        itemService.saveManageItems(userPrincipal,saveManageItemDTO,
                new MockMultipartFile[]{});
        // Assert
        verify(documentUnitRepository,times(1)).save(any());
    }

    @Test
    void whenSaveManageItemsWhenTPAIdEqualZeroThenItsSuccessfully() {
        //Arrange
        UserPrincipal userPrincipal = SecurityData.getUserDefaultStatic();
        SaveManageItemDTO saveManageItemDTO = ItemData.getSaveManageItemDto();
        saveManageItemDTO.setIdTpaItem(0);
        MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain", "some xml".getBytes());
        MockMultipartFile secondFile = new MockMultipartFile("files", "other-file-name.png", "text/plain", "some other type".getBytes());
        DocumentUnit documentUnit = ItemData.getDocumentUnit();
        when(documentUnitRepository.save(any())).thenReturn(documentUnit);
        when(documentUnitRepository.findById(anyInt())).thenReturn(Optional.ofNullable(documentUnit));
        // Act
        itemService.saveManageItems(userPrincipal,saveManageItemDTO,
                new MockMultipartFile[]{});
        // Assert
        verify(documentUnitRepository,times(1)).save(any());
    }

    @Test
    void whenSaveManageItemsThenThrowProgramaAvisosExceptio() {
        //Arrange
        UserPrincipal userPrincipal = SecurityData.getUserDefaultStatic();
        SaveManageItemDTO saveManageItemDTO = ItemData.getSaveManageItemDto();
        when(documentUnitRepository.getByFilter(any())).thenThrow(ProgramaAvisosException.class);
        // Act & Assert
        Assertions.assertThrows(ProgramaAvisosException.class, () ->
                itemService.saveManageItems(userPrincipal, saveManageItemDTO, new MockMultipartFile[]{}));

    }

    @Test
    void whenGetFileExtensionThenItsSuccessfully() {
        MockMultipartFile firstFile = new MockMultipartFile("files", "filename.txt", "text/plain", "some xml".getBytes());
        String name = itemService.getFileExtension(firstFile);
        Assertions.assertFalse(name.isEmpty());
        Assertions.assertEquals("txt",name);
    }

    @Test
    void whenGetFileExtensionThenItsReturnNull() {
        MockMultipartFile firstFile = new MockMultipartFile("files", "filename", "text/plain", "some xml".getBytes());
        String name = itemService.getFileExtension(firstFile);
        Assertions.assertNull(name);
    }

}