package com.guru.userManagementSystem.security.authentication.config;

import com.guru.userManagementSystem.security.service.UamUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UmsUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final UamUserDetailsService uamUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails uamUserDetails = uamUserDetailsService.loadUserByUsername(username);

        if (passwordEncoder.matches(password, uamUserDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, uamUserDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
