package com.hkgroup.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate dob;
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private Set<GroupHasUser> users = new HashSet<>();
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private  Set<UserHasRole> roles = new HashSet<>();
}
