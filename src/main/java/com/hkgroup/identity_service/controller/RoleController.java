package com.hkgroup.identity_service.controller;

import java.util.List;

import com.hkgroup.identity_service.dto.request.RoleRequest;
import com.hkgroup.identity_service.dto.response.ApiResponse;
import com.hkgroup.identity_service.dto.response.RoleResponse;
import com.hkgroup.identity_service.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> createRole(@RequestBody @Valid RoleRequest request) {
        RoleResponse result = roleService.create(request);
        return ApiResponse.<RoleResponse>builder().result(result).build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRole() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    public ApiResponse<Void> delete(@PathVariable String role) {
        System.out.println("DELETE ROLE");
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }
}
