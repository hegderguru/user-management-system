package com.guru.userManagementSystem.security.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;


@Data
@Entity
@Table(name = "ums_user_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "ums_user_info_seq")
    @SequenceGenerator(name = "ums_user_info_seq", sequenceName = "ums_user_info_seq", initialValue = 100000, allocationSize = 20)
    private Long id;

    private String password;

    private final String username;

    @ManyToMany
    @JoinTable(name = "ums_user_info_granted_authority",joinColumns = @JoinColumn(name = "ums_user_info_id"),inverseJoinColumns = @JoinColumn(name = "ums_user_granted_authority_id"))
    private final Set<GrantedAuthority> authorities;

    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialsNonExpired;

    private final boolean enabled;

    public User(String username, Set<GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
        this.username = username;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }
}
