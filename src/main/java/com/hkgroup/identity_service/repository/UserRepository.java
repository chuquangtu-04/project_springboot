package com.hkgroup.identity_service.repository;

import com.hkgroup.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    String id(String id);
    boolean existsByUserName(String userName);
}
