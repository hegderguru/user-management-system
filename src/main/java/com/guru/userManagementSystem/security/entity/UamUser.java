package com.guru.userManagementSystem.security.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;


@Data
@Entity
@Table(name = "ums_user")
public class UamUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ums_user_seq")
    @SequenceGenerator(name = "ums_user_seq", sequenceName = "ums_user_seq", initialValue = 100000, allocationSize = 20)
    private Long id;

    private String password;

    private final String username;

    @ManyToMany
    @JoinTable(name = "ums_user_granted_authority",joinColumns = @JoinColumn(name = "ums_user_id"),inverseJoinColumns = @JoinColumn(name = "ums_user_granted_authority_id"))
    private final Set<UamGrantedAuthority> authorities;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;

    public UamUser(String username, Set<UamGrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.username = username;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }
}
