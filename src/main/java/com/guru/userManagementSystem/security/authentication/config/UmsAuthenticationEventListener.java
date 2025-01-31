package com.guru.userManagementSystem.security.authentication.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UmsAuthenticationEventListener {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        log.info("Login Success for {}", successEvent.getAuthentication().getName());
    }

    @EventListener
    public void onSuccess(AbstractAuthenticationFailureEvent failureEvent) {
        log.info("Login Failed for {}", failureEvent.getAuthentication().getName());
    }
}
