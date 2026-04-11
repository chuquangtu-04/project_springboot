package com.hkgroup.identity_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkgroup.identity_service.dto.request.UserCreationRequest;
import com.hkgroup.identity_service.dto.response.UserResponse;
import com.hkgroup.identity_service.entity.User;
import com.hkgroup.identity_service.exception.AppException;
import com.hkgroup.identity_service.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import  static org.mockito.Mockito.when;
import  static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private LocalDate dob;
    private User user;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2004, 1,1);
        userCreationRequest = UserCreationRequest.builder()
                .userName("Chu Quang Tú")
                .firstName("Chu Quang")
                .lastName("Tus")
                .dob(dob)
                .password("12345678")
                .phone("0336572907")
                .build();
        userResponse = UserResponse.builder()
                .id("adb1221313123")
                .userName("Chu Quang Tú")
                .firstName("Chu Quang")
                .lastName("Tus")
                .dob(dob)
                .phone("0336572907")
                .build();
        user = User.builder()
                .id(12)
                .userName("Chu Quang Tú")
                .firstName("Chu Quang")
                .lastName("Tus")
                .dob(dob)
                .phone("0336572907")
                .build();
    }
    @Test
    void createUser_validRequest_success() {
        // GIVE
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        // WHEN
        var response = userService.createRequest(userCreationRequest);

        // THEN
        Assertions.assertThat(response.getId()).isEqualTo("12");
        Assertions.assertThat(response.getUserName()).isEqualTo("Chu Quang Tú");
    }
    @Test
    void createUser_validRequest_fail() {
        // GIVE
        when(userRepository.existsByUserName(anyString())).thenReturn(true);
        var exception =  assertThrows(AppException.class, () -> userService.createRequest(userCreationRequest));

        // THEN
        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }
    @Test
    @WithMockUser(username = "Chu Quang Tú")
    void getMyInfo_validRequest_success() {
        when(userRepository.findByuserName(anyString())).thenReturn(Optional.of(user));

        // When
        var response = userService.getMyInfo();

        Assertions.assertThat(response.getId()).isEqualTo("12");
        Assertions.assertThat(response.getUserName()).isEqualTo("Chu Quang Tú");
    }
    @Test
    @WithMockUser(username = "Chu Quang Tú")
    void getMyInfo_validRequest_fail() {
//        when(userRepository.findByuserName(anyString())).thenReturn(Optional.of(user));

        var exception =  assertThrows(AppException.class, () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
    }
}
