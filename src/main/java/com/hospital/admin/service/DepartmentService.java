package com.hospital.admin.service;

import com.hospital.admin.dto.request.DepartmentRequest;
import com.hospital.admin.dto.response.DepartmentResponse;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse createDepartment(DepartmentRequest request);
    DepartmentResponse getDepartmentById(Long id);
    List<DepartmentResponse> getAllDepartments();
    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);
    void deleteDepartment(Long id);
    void toggleDepartmentStatus(Long id);
}
