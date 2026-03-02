package com.hkgroup.identity_service.repository;

import com.hkgroup.identity_service.entity.Permission;
import com.hkgroup.identity_service.entity.Role;
import com.hkgroup.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
        @Query("select r from Role r " +
                "join UserHasRole ur on r.id = ur.role.id " +
                "where ur.user.id = :userId")
        List<Role> getAllByUserId(@Param("userId") Integer userId);
        @Query("select u from User u " +
                "join UserHasRole ur on u.id = ur.user.id " +
                "where ur.role.id = :roleId")
        List<User> getAllByRoleId(@Param("roleId") Integer roleId);
        @Query("select p from Permission p " +
        "join RoleHasPermission rhp on p.id = rhp.permission.id " +
        "where rhp.role.id = :roleId")
        List<Permission> getAllPermissionId(@Param("roleId") Integer roleId);
        @Query("select p from Permission p " +
        "join RoleHasPermission rhp on rhp.permission.id = p.id " +
        "join UserHasRole uhr on rhp.role.id = uhr.role.id " +
        "where uhr.user.id = :userId")
        List<Permission> getPermissionsByUserId(@Param("userId") Integer userId);
}
