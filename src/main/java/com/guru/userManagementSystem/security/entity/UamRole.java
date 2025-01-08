package com.guru.userManagementSystem.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
@Entity
@Table(name = "ums_user_role")
public class UamRole {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ums_user_role_seq")
    @SequenceGenerator(name = "ums_user_role_seq", sequenceName = "ums_user_role_seq", initialValue = 100000, allocationSize = 20)
    private Long id;

    private String roleName;

    @OneToMany
    @JoinTable(name = "ums_user_role_permission",joinColumns = @JoinColumn(name = "user_role_id"),inverseJoinColumns = @JoinColumn(name = "user_permission_id"))
    List<UamPermission> uamPermissions;
}