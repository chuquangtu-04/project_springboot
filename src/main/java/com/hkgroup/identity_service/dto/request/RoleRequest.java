package com.hkgroup.identity_service.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {
    @NotBlank(message = "Role name must not be blank")
    String name;

    @NotBlank(message = "Description must not be blank")
    String description;

    Set<String> permissions;
}
