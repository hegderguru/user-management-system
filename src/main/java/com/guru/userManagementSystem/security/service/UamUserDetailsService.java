package com.guru.userManagementSystem.security.service;

import com.guru.userManagementSystem.security.entity.UamUser;
import com.guru.userManagementSystem.security.repository.UserRepository;
import com.guru.userManagementSystem.security.response.UamUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UamUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    static Map<String, UserDetails> userMap = new HashMap<>();

    static {
        PasswordEncoder delegatingPasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        /*userMap.put("guruhegde", new UserDetails("guruhegde", null, true, true, true, true));
        userMap.get("guruhegde").setPassword(delegatingPasswordEncoder.encode("guruhegde"));*/
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UamUser byUsername = userRepository.findByUsername(username);
        return UamUserDetails.builder().authorities(byUsername.getAuthorities())
                .credentialsNonExpired(byUsername.isCredentialsNonExpired())
                .accountNonLocked(byUsername.isAccountNonLocked())
                .accountNonExpired(byUsername.isAccountNonExpired())
                .enabled(byUsername.isEnabled())
                .password(byUsername.getPassword())
                .username(byUsername.getUsername())
                .build();
    }
}
