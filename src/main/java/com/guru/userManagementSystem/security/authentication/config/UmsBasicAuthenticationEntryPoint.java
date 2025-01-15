package com.guru.userManagementSystem.security.authentication.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Objects;

public class UmsBasicAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setHeader("UMS-AUTH_ERROR", "Authentication failure");
        //response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(AdminConstants.OBJECT_MAPPER.writeValueAsString(HttpResponseBody
                .builder()
                .error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .application(AdminConstants.UMS_APPLICATION_NAME)
                .message(Objects.isNull(authException)?"Un-Authorised Access Denied":authException.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.UNAUTHORIZED.toString())
                .build()));
    }
}
