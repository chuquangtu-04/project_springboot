package com.hkgroup.identity_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hkgroup.identity_service.dto.request.UserCreationRequest;
import com.hkgroup.identity_service.dto.response.UserResponse;
import com.hkgroup.identity_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.time.LocalDate;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {
    @Container
    static final MySQLContainer<?> MY_SQL_CONTAINER = new MySQLContainer<>("mysql:8.0.36")
            .withDatabaseName("testdb")
            .withUsername("root")
            .withPassword("123456")
            .withStartupTimeout(Duration.ofSeconds(60));

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MY_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MY_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MY_SQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driverClassName", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");
    }
    @Autowired
    private MockMvc mockMvc;
    private UserService userService;
    private UserCreationRequest userCreationRequest;
    private UserResponse userResponse;
    private LocalDate dob;
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
    }

    @Test
    void createUser_validRequest_success() throws Exception {
        // GIVEN
//        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(userCreationRequest);
        // WHEN, THEN
        var response = mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("result.userName")
                        .value("Chu Quang Tú"));
        log.info("Result: {}", response.andReturn().getResponse().getContentAsString());

    }
}
