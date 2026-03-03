package com.hkgroup.identity_service.service;

import com.hkgroup.identity_service.dto.request.UserCreationRequest;
import com.hkgroup.identity_service.dto.request.UserUpdateRequest;
import com.hkgroup.identity_service.dto.response.UserResponse;
import com.hkgroup.identity_service.entity.User;
import com.hkgroup.identity_service.entity.UserHasRole;
import com.hkgroup.identity_service.enums.Role;
import com.hkgroup.identity_service.exception.AppException;
import com.hkgroup.identity_service.exception.ErrorCode;
import com.hkgroup.identity_service.mapper.UserMapper;
import com.hkgroup.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User createRequest(UserCreationRequest request) {
        if(userRepository.existsByUserName(request.getUserName())) {
            throw new AppException(ErrorCode.USER_EXISTED);
//            throw new RuntimeException(ErrorCode.USER_EXISTED);
        }

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRole(roles);

        return  userRepository.save(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
//        return  userRepository.findAll();
        log.info("In method get user");
        return userMapper.toUserResponseList(userRepository.findAll());
    }

    @PostAuthorize("hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found")));
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();

        User byUsername = userRepository.findByuserName(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(byUsername);
    }

    public UserResponse updateUser(String id, UserUpdateRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, req);
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public User deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
        return user;
    }
}
