package com.example.appsecurity.repositories;

import com.example.appsecurity.entity.Role;
import com.example.appsecurity.enums.RoleNames;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepositiory extends JpaRepository<Role, UUID> {
    List<Role> findAllByRole(RoleNames roleNames);
}
