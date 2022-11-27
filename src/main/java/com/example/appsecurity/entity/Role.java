package com.example.appsecurity.entity;

import com.example.appsecurity.enums.RoleNames;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(value = EnumType.STRING)
    private RoleNames role;

    @Override
    public String getAuthority() {
        return this.role.name();
    }
}
