package com.hkgroup.identity_service.mapper;

import java.util.List;

import com.hkgroup.identity_service.dto.request.PermissionRequest;
import com.hkgroup.identity_service.dto.response.PermissionResponse;
import com.hkgroup.identity_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

    List<Permission> toPermissionResponseList(List<Permission> permissions);
}
