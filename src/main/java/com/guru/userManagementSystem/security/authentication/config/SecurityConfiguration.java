package com.guru.userManagementSystem.security.authentication.config;

import com.guru.userManagementSystem.security.exception.UmsAccessDeniedHanlder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Profile("prod")
@Configuration
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer ->
                httpSecuritySessionManagementConfigurer.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession).invalidSessionUrl("/error").maximumSessions(1).maxSessionsPreventsLogin(true).expiredUrl("/sessionExpired"));
        //httpSecurity.sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession));
        //Spring default is change session id
        httpSecurity.requiresChannel(channelRequestMatcherRegistry -> channelRequestMatcherRegistry.anyRequest().requiresSecure())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/h2-console/**", "register", "authenticate","error","sessionExpired").permitAll().anyRequest().authenticated());
        //httpSecurity.formLogin(AbstractHttpConfigurer::disable);
        httpSecurity.formLogin(Customizer.withDefaults());
        //httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.authenticationEntryPoint(new UmsBasicAuthenticationEntryPoint()));
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        //httpSecurity.headers(AbstractHttpConfigurer::disable);
        httpSecurity.headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        //httpSecurity.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint(new UmsBasicAuthenticationEntryPoint())); //Globalconfig
        httpSecurity.exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new UmsAccessDeniedHanlder()));
        return httpSecurity.build();
    }

    public InitializingBean initializingBean(){
        //return ()-> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);// If you use @Async
        //return ()-> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_GLOBAL);// Access for all thread; used for Desktop apps
        return ()-> SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_THREADLOCAL);// Default
    }

}
