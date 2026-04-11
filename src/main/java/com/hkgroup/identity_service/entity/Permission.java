package com.hkgroup.identity_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_permission")
public class Permission {
    @Id
    private String name;

    private String description;
}
