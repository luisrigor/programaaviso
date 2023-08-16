package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.*;
import com.gsc.programaavisos.security.UserPrincipal;
import org.springframework.web.multipart.MultipartFile;

public interface ProgramaAvisosService {
    void savePA(UserPrincipal userPrincipal, PADTO pa);
    void removePA(UserPrincipal userPrincipal,Integer id,String removedOption,String removedObs);
    FilterBean searchPA(UserPrincipal userPrincipal, SearchPADTO searchPADTO);
    void uploadFile(UserPrincipal userPrincipal, MultipartFile file);
}
