package com.guru.userManagementSystem.security.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ums_user_granted_authority")
public class UmsGrantedAuthority implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ums_user_granted_authority_seq")
    @SequenceGenerator(name = "ums_user_granted_authority_seq", sequenceName = "ums_user_granted_authority_seq", initialValue = 100000, allocationSize = 20)
    private Long id;

    private String authorityName;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "ums_user_authority_role", joinColumns = @JoinColumn(name = "user_authority_id"), inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private List<UamRole> uamRoles;

    @Override
    public String getAuthority() {
        return authorityName;
    }
}
