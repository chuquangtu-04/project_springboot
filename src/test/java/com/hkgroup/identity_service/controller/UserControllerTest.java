package com.hkgroup.identity_service.controller;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkgroup.identity_service.dto.request.UserCreationRequest;
import com.hkgroup.identity_service.dto.response.UserResponse;
import com.hkgroup.identity_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private LocalDate dob;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void initData() {
        dob = LocalDate.of(2004, 1, 1);
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
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
        //        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequest);
        Mockito.when(userService.createRequest(ArgumentMatchers.any())).thenReturn(userResponse);
        // WHEN, THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //                .andExpect(MockMvcResultMatchers.jsonPath("code")
                //                        .value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.id").value("adb1221313123"));
    }
}
