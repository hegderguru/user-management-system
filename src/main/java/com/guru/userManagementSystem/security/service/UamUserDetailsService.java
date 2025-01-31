package com.guru.userManagementSystem.security.service;

import com.guru.userManagementSystem.security.authentication.config.AdminConstants;
import com.guru.userManagementSystem.security.entity.UamUser;
import com.guru.userManagementSystem.security.repository.UserRepository;
import com.guru.userManagementSystem.security.response.UamUserDetails;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Data
@Service
public class UamUserDetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    static Map<String, UamUserDetails> userMap = new HashMap<>();

    static {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        userMap.put("guruhegde", UamUserDetails.builder().authorities(new HashSet<>())
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .enabled(true)
                .password(delegatingPasswordEncoder.encode("guruhegde"))
                .username("guruhegde")
                .build());
    }

    @Override
    public UamUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UamUser byUsername = userRepository.findByUsername(username);
        return buildBuildUamUserDetails(byUsername);
        //return userMap.get(username);
    }

    private UamUserDetails buildBuildUamUserDetails(UamUser byUsername) {
        return UamUserDetails.builder().authorities(byUsername.getAuthorities())
                .credentialsNonExpired(byUsername.isCredentialsNonExpired())
                .accountNonLocked(byUsername.isAccountNonLocked())
                .accountNonExpired(byUsername.isAccountNonExpired())
                .enabled(byUsername.isEnabled())
                .password(byUsername.getPassword())
                .username(byUsername.getUsername())
                .build();
    }

    public UamUserDetails createUamUser(UamUserDetails uamUserDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!AdminConstants.UMS_ADMIN_NAME.equals(authentication.getName())) {
            throw new UsernameNotFoundException("User not allowed to create users!");
        }
        return buildBuildUamUserDetails(userRepository.save(buildUamUser(uamUserDetails)));
    }

    public UamUser buildUamUser(UamUserDetails uamUserDetails) {
        return UamUser.builder().username(uamUserDetails.getUsername())
                .password(passwordEncoder.encode(uamUserDetails.getPassword()))
                .enabled(uamUserDetails.isEnabled())
                .accountNonExpired(uamUserDetails.isAccountNonExpired())
                .authorities(new HashSet<>())
                .accountNonLocked(uamUserDetails.isAccountNonLocked())
                .credentialsNonExpired(uamUserDetails.isCredentialsNonExpired())
                .build();
    }
}
