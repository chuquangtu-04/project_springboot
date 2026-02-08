package com.hkgroup.identity_service.service;

import com.hkgroup.identity_service.dto.request.UserCreationRequest;
import com.hkgroup.identity_service.dto.request.UserUpdateRequest;
import com.hkgroup.identity_service.entity.User;
import com.hkgroup.identity_service.exception.AppException;
import com.hkgroup.identity_service.exception.ErrorCode;
import com.hkgroup.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createRequest(UserCreationRequest request) {
        User user = new User();

        if(userRepository.existsByUserName(request.getUserName())) {
//            throw new AppException(ErrorCode.USER_EXISTED);
            throw new RuntimeException("ErrorCode.USER_EXISTED");
        }

        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDob(request.getDob());

        return  userRepository.save(user);
    }

    public List<User> getUsers() {
        return  userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String id, UserUpdateRequest req) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if(req.getUserName() != null) {
            user.setUserName(req.getUserName());
        }
        if(req.getFirstName() != null) {
            user.setFirstName(req.getFirstName());
        }
        if(req.getLastName() != null) {
            user.setLastName(req.getLastName());
        }
        if(req.getDob() != null) {
            user.setDob(req.getDob());
        }

        return userRepository.save(user);
    }

    public User deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(id);
        return user;
    }
}
