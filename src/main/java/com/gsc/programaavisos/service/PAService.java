package com.gsc.programaavisos.service;

import com.gsc.programaavisos.dto.PADTO;
import com.gsc.programaavisos.security.UserPrincipal;

public interface PAService {
     void savePA(UserPrincipal userPrincipal, PADTO pa);
}
