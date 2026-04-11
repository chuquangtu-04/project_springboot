package com.hkgroup.identity_service.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_group")
public class Group {
    @Id
    private String name;

    private String description;

    @OneToOne
    private Role role;

    @ManyToMany
    Set<User> users;
}
