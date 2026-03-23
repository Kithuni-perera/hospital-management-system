package com.hospital.admin.service;

import com.hospital.admin.dto.request.UserRequest;
import com.hospital.admin.dto.response.UserResponse;
import com.hospital.admin.enums.Role;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    List<UserResponse> getUsersByRole(Role role);
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
    void toggleUserStatus(Long id);
}
