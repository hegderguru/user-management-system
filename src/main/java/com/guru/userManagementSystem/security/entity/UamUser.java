package com.guru.userManagementSystem.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "ums_user")
public class UamUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ums_user_seq")
    @SequenceGenerator(name = "ums_user_seq", sequenceName = "ums_user_seq", initialValue = 100000, allocationSize = 20)
    private Long id;

    private String password;

    private String username;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UamGrantedAuthority> authorities = new HashSet<>();

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

    public UamUser(String username, Set<UamGrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.username = username;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }
}
