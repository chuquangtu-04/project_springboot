package com.hkgroup.identity_service.dto.response;

import com.hkgroup.identity_service.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    String id;
    String userName;
    String firstName;
    String lastName;
    String phone;
    LocalDate dob;
    Set<RoleResponse> roles;
}
