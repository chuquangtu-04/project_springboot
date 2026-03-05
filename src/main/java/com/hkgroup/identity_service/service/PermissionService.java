package com.hkgroup.identity_service.service;

import com.hkgroup.identity_service.dto.request.PermissionRequest;
import com.hkgroup.identity_service.dto.response.ApiResponse;
import com.hkgroup.identity_service.dto.response.PermissionResponse;
import com.hkgroup.identity_service.entity.Permission;
import com.hkgroup.identity_service.exception.AppException;
import com.hkgroup.identity_service.exception.ErrorCode;
import com.hkgroup.identity_service.mapper.PermissionMapper;
import com.hkgroup.identity_service.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    public PermissionResponse create(PermissionRequest request) {
        if(permissionRepository.existsPermissionByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
