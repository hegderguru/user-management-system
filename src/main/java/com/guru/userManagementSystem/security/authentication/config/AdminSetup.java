package com.guru.userManagementSystem.security.authentication.config;

import com.guru.userManagementSystem.security.entity.UamGrantedAuthority;
import com.guru.userManagementSystem.security.entity.UamPermission;
import com.guru.userManagementSystem.security.entity.UamRole;
import com.guru.userManagementSystem.security.entity.UamUser;
import com.guru.userManagementSystem.security.service.UamUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Component
public class AdminSetup {

    @Autowired
    UamUserDetailsService uamUserDetailsService;

    @EventListener(SpringApplicationEvent.class)
    public void setup() {
        UamUser byUsername = uamUserDetailsService.getUserRepository().findByUsername(AdminConstants.UMS_ADMIN_NAME);
        if (Objects.isNull(byUsername)) {
            UamUser admiUser = UamUser.builder()
                    .username(AdminConstants.UMS_ADMIN_NAME)
                    .password(AdminConstants.UMS_ADMIN_NAME)
                    .accountNonExpired(true)
                    .enabled(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .authorities(new HashSet<>(List.of(UamGrantedAuthority.builder()
                            .authorityName(AdminConstants.UMS_ADMIN_AUTHORITY_NAME)
                            .uamRoles(new ArrayList<>(List.of(UamRole.builder()
                                    .roleName(AdminConstants.UMS_ADMIN_ROLE_NAME)
                                    .uamPermissions(new ArrayList<>(List.of(UamPermission.builder()
                                            .write(true)
                                            .read(true)
                                            .update(true)
                                            .delete(true)
                                            .className(null)
                                            .fieldName(null)
                                            .build())))
                                    .build())))
                            .build()))).build();
            uamUserDetailsService.getUserRepository().save(admiUser);
        }
    }

}
