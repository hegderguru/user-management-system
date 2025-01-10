package com.guru.userManagementSystem.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ums_user_permission")
public class UmsPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ums_user_permission_seq")
    @SequenceGenerator(name = "ums_user_permission_seq", sequenceName = "ums_user_permission_seq", initialValue = 100000, allocationSize = 20)
    private Long id;

    private String className; // full qualifier
    private String fieldName; // just name

    private boolean read;
    private boolean write;
    private boolean update;
    private boolean delete;
}
