package com.guru.userManagementSystem.security.authentication.config;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Data
public class HttpResponseBody {

    private String application = AdminConstants.UMS_APPLICATION_NAME;
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    private String status;
    private String error;
    private String message;
    private String path;

}
