package com.hkgroup.identity_service.repository;

import com.hkgroup.identity_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Integer id(Integer id);
    boolean existsByUserName(String userName);
    Optional<User> findByuserName(String userName);
}
