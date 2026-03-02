package com.hkgroup.identity_service.service;

import com.hkgroup.identity_service.dto.request.AuthenticationRequest;
import com.hkgroup.identity_service.dto.response.ApiResponse;
import com.hkgroup.identity_service.dto.response.AuthenticationResponse;
import com.hkgroup.identity_service.dto.response.UserResponse;
import com.hkgroup.identity_service.exception.AppException;
import com.hkgroup.identity_service.exception.ErrorCode;
import com.hkgroup.identity_service.mapper.UserMapper;
import com.hkgroup.identity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    UserMapper userMapper;

    public AuthenticationResponse authenticated(AuthenticationRequest request) {
        System.out.println("Request: "+ request.getUserName());
        var user = userRepository.findByuserName(request.getUserName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        Boolean result = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!result) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return userMapper.toAuthenticationResponse(result);
    }
}
