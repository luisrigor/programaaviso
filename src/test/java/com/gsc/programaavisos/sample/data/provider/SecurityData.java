package com.gsc.programaavisos.sample.data.provider;

import com.gsc.programaavisos.constants.AppProfile;
import com.gsc.programaavisos.model.crm.entity.LoginKey;
import com.gsc.programaavisos.security.UserPrincipal;
import com.rg.dealer.Dealer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SecurityData {

    private static final String ISSUER = "Tcap";
    private static final String AUDIENCE = "Tcap Clients";
    private static final String ROLES = "roles";
    private static final String JWT_ENVIRONMENT = "environment";
    private static final String JWT_CLIENT_ID = "client";

    /**
     * Dudas sobre el uso de estas variables estaticas
     * según las reglas de negocio.
     */

    public static  final  String ACTIVE_PROFILE = "local";
    private static final String CREATED_BY = "Tcap-TokenProvider";

    public LoginKey getLoginKey(){
//      SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        //This key was generated for testing purpouses
        String key = "T5LyHroP0hxSTDeOyh1knSH1ZqWJyJ9KHb7T7nQNkPvgsRI2xKV9OIPOugSf6bXKLRXPkfB07YXJ03EhzPC/kg==";
        LoginKey loginKey = new LoginKey();
        loginKey.setId(1L);
//      loginKey.setKeyValue(Encoders.BASE64.encode(key.getEncoded()));
        loginKey.setKeyValue(key);
        loginKey.setEnabled(true);
        loginKey.setCreatedBy(CREATED_BY);
        loginKey.setCreated(LocalDateTime.now());
        return loginKey;
    }

    public String generateNewToken() {
        Set<AppProfile> roles = new HashSet<>();
        roles.add(AppProfile.TOYOTA_LEXUS_PRF_MANAGER_CA);
        UserPrincipal userPrincipal = new UserPrincipal("137||tcap1@tpo||tiago.fernandes@parceiro.rigorcg.pt",
                roles, 1L);
        Date now = new Date();
        Date expiryDate =  new Date(now.getTime() + 648000L);
        LoginKey loginKey =  getLoginKey();

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(userPrincipal.getUsername())
                .setAudience(AUDIENCE)
                .setExpiration(expiryDate)
                .setIssuedAt(new Date())
                .setId(UUID.randomUUID().toString())
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(loginKey.getKeyValue())))
                .setHeaderParam("kid", loginKey.getId())
                .claim(JWT_ENVIRONMENT, ACTIVE_PROFILE)
                .claim(JWT_CLIENT_ID, userPrincipal.getClientId())
                .claim(ROLES, userPrincipal.getRoles())
                .compact();
    }

    public UserPrincipal getUser() {
        Set<AppProfile> roles = new HashSet<>();
        roles.add(AppProfile.ROLE_VIEW_CALL_CENTER_DEALERS);
        return new UserPrincipal("137||tcap1@tpo||tiago.fernandes@parceiro.rigorcg.pt",
                roles,59L);
    }

    public static UserPrincipal getUserDefaultStatic() {
        Set<AppProfile> roles = new HashSet<>();
        roles.add(AppProfile.TOYOTA_LEXUS_PRF_TCAP);
        UserPrincipal userPrincipal = new UserPrincipal("137||tcap1@tpo||tiago.fernandes@parceiro.rigorcg.pt",
                roles,59L);
        userPrincipal.setOidNet(Dealer.OID_NET_TOYOTA);
        return userPrincipal;
    }


}
