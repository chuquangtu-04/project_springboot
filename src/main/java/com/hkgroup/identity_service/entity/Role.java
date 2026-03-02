package com.hkgroup.identity_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_role")
public class Role {
    @Id
    private Integer id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    private Set<RoleHasPermission> permissions = new HashSet<>();
    @OneToMany(mappedBy = "role")
    @ToString.Exclude
    private Set<UserHasRole> roles = new HashSet<>();
}
