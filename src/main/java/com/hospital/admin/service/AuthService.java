package com.hospital.admin.service;

import com.hospital.admin.dto.request.LoginRequest;
import com.hospital.admin.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
