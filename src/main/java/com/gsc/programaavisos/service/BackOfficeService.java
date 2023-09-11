package com.gsc.programaavisos.service;

import com.gsc.programaavisos.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

public interface BackOfficeService {

    void importTechnicalCampaign(UserPrincipal userPrincipal, MultipartFile file);
}
