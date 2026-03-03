package com.hkgroup.identity_service.controller;

import com.hkgroup.identity_service.dto.request.UserCreationRequest;
import com.hkgroup.identity_service.dto.request.UserUpdateRequest;
import com.hkgroup.identity_service.dto.response.ApiResponse;
import com.hkgroup.identity_service.dto.response.UserResponse;
import com.hkgroup.identity_service.entity.User;
import com.hkgroup.identity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        System.out.println("GetName: "+ request.getUserName());
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createRequest(request));
        return apiResponse;
    }

//    @GetMapping
//    List<User> getUsers() {
//        return  userService.getUsers();
//    }
    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("UserName: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }
    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfoUser() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @GetMapping("/{userId}")
    UserResponse getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PatchMapping("/{userId}")
    UserResponse updateUser(
            @PathVariable("userId") String userId,
            @RequestBody UserUpdateRequest request
    ) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    User deleteUser(@PathVariable("userId") String userId) {
        return userService.deleteUser(userId);
    }
}
