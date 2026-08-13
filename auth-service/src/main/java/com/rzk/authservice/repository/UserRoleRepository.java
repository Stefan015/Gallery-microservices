package com.rzk.authservice.repository;

import com.rzk.authservice.model.UserRole;
import com.rzk.authservice.model.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
    List<UserRole> findByUser_Id(Long userId);
}