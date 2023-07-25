package com.gsc.programaaviso.dto;


import com.gsc.programaaviso.constants.AppProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;


@AllArgsConstructor
@Getter
public class UserDTO {

    private final String token;
    private final Set<AppProfile> roles;
    private final Long clientId;
}
