package com.guru.userManagementSystem.security.repository;

import com.guru.userManagementSystem.security.entity.UamUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UamUser,Long> {
    UamUser findByUsername(String username);
}
