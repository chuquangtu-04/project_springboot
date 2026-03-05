package com.hkgroup.identity_service.mapper;

import com.hkgroup.identity_service.dto.request.PermissionRequest;
import com.hkgroup.identity_service.dto.request.UserCreationRequest;
import com.hkgroup.identity_service.dto.request.UserUpdateRequest;
import com.hkgroup.identity_service.dto.response.AuthenticationResponse;
import com.hkgroup.identity_service.dto.response.PermissionResponse;
import com.hkgroup.identity_service.dto.response.UserResponse;
import com.hkgroup.identity_service.entity.Permission;
import com.hkgroup.identity_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
    List<Permission> toPermissionResponseList(List<Permission> permissions);
}
