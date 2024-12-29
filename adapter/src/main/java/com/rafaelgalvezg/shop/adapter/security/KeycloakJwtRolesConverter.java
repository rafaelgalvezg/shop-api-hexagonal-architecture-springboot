package com.rafaelgalvezg.shop.adapter.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class KeycloakJwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    public static final String PREFIX_REALM_ROLE = "ROLE_realm_";
    public static final String PREFIX_RESOURCE_ROLE = "ROLE_";
    public static final String CLAIM_REALM_ACCESS = "realm_access";
    public static final String CLAIM_RESOURCE_ACCESS = "resource_access";
    public static final String CLAIM_ROLES = "roles";

    @Override
   public Collection<GrantedAuthority> convert(Jwt jwt) {
    Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    // Realm roles
    Map<String, Collection<String>> realmAccess = jwt.getClaim(CLAIM_REALM_ACCESS);
    if (!CollectionUtils.isEmpty(realmAccess)) {
        Collection<String> roles = realmAccess.get(CLAIM_ROLES);
        if (!CollectionUtils.isEmpty(roles)) {
            roles.stream()
                 .map(role -> new SimpleGrantedAuthority(PREFIX_REALM_ROLE + role))
                 .forEach(grantedAuthorities::add);
        }
    }

    // Resource (client) roles
    Map<String, Map<String, Collection<String>>> resourceAccess = jwt.getClaim(CLAIM_RESOURCE_ACCESS);
    if (!CollectionUtils.isEmpty(resourceAccess)) {
        resourceAccess.forEach((resource, resourceClaims) -> resourceClaims.get(CLAIM_ROLES).forEach(
                role -> grantedAuthorities.add(new SimpleGrantedAuthority(PREFIX_RESOURCE_ROLE + resource + "_" + role))
        ));
    }

    return grantedAuthorities;
}
}
