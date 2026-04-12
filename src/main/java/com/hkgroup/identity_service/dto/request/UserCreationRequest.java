package com.hkgroup.identity_service.dto.request;

import java.time.LocalDate;

import com.hkgroup.identity_service.util.PhoneNumber;
import com.hkgroup.identity_service.validator.DobContraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 6, message = "USER_INVALID")
    String userName;

    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;

    @PhoneNumber(message = "PHONE_INVALID")
    String phone;

    @DobContraint(min = 16, message = "INVALID_DOB")
    LocalDate dob;
}
