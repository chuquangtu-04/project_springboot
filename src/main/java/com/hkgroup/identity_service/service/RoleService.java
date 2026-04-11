package com.hkgroup.identity_service.service;

import java.util.HashSet;
import java.util.List;

import com.hkgroup.identity_service.dto.request.RoleRequest;
import com.hkgroup.identity_service.dto.response.RoleResponse;
import com.hkgroup.identity_service.exception.AppException;
import com.hkgroup.identity_service.exception.ErrorCode;
import com.hkgroup.identity_service.mapper.RoleMapper;
import com.hkgroup.identity_service.repository.PermissionRepository;
import com.hkgroup.identity_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    public RoleResponse create(RoleRequest request) {
        if (roleRepository.existsRoleByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }

        var role = roleMapper.toRole(request);
        var permission = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permission));
        role = roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roleMapper.toRoleResponseList(roles);
    }

    public void delete(String role) {
        roleRepository.deleteById(role);
    }
}
