package com.gsc.programaaviso.controller;


import com.gsc.programaaviso.dto.UserDTO;
import com.gsc.programaaviso.security.JwtAuthenticationToken;
import com.gsc.programaaviso.security.TokenProvider;
import com.gsc.programaaviso.security.UserPrincipal;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(value = "",tags = "Programa Avisos - Authentication")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    @PostMapping("/sign-in/{appId}")
    public UserDTO createAuthenticationToken(@RequestHeader("loginToken") String loginToken, @PathVariable String appId) {

        loginToken = appId+"|||"+loginToken;
        Authentication authentication = authenticationManager.authenticate(new JwtAuthenticationToken(loginToken));
        String token = tokenProvider.createToken(authentication, appId);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return new UserDTO(token, userPrincipal.getRoles(), userPrincipal.getClientId());
    }
}
