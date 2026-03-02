package com.hkgroup.identity_service.service;

import com.hkgroup.identity_service.entity.Permission;
import com.hkgroup.identity_service.entity.Role;
import com.hkgroup.identity_service.entity.User;
import com.hkgroup.identity_service.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public record RoleService(RoleRepository roleRepository) {
    @PostConstruct
    public void printAllRoles() {
//        List<Role> userRole = roleRepository.getAllByUserId(1);
//        for (Role roles: userRole) {
//            System.out.println("Role Id: "+roles.getId());
//            List<Permission> result = roleRepository.getAllPermissionId(roles.getId());
//            System.out.println("Result:"+ result);
//        }
        List<Permission> permissions = roleRepository.getPermissionsByUserId(1);
        for(Permission permission: permissions) {
            System.out.println("Vai trò: "+ permission.getName());
        }

    }
}
