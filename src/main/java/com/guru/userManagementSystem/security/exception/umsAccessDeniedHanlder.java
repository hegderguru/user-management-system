package com.guru.userManagementSystem.security.exception;

import com.guru.userManagementSystem.security.authentication.config.AdminConstants;
import com.guru.userManagementSystem.security.authentication.config.HttpResponseBody;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.Objects;

public class umsAccessDeniedHanlder implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("UMS-AUTH_DEINED", "Authentication failure");
        //response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write(AdminConstants.OBJECT_MAPPER.writeValueAsString(HttpResponseBody
                .builder()
                .error(HttpStatus.FORBIDDEN.getReasonPhrase())
                .application(AdminConstants.UMS_APPLICATION_NAME)
                .message(Objects.isNull(accessDeniedException)?"Authorisation failed!":accessDeniedException.getMessage())
                .path(request.getRequestURI())
                .status(HttpStatus.FORBIDDEN.toString())
                .build()));
    }
}
