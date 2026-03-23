package com.hospital.admin.service.impl;

import com.hospital.admin.dto.request.DepartmentRequest;
import com.hospital.admin.dto.response.DepartmentResponse;
import com.hospital.admin.entity.Department;
import com.hospital.admin.exception.DuplicateResourceException;
import com.hospital.admin.exception.ResourceNotFoundException;
import com.hospital.admin.repository.DepartmentRepository;
import com.hospital.admin.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Department already exists: " + request.getName());
        }
        Department department = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .phone(request.getPhone())
                .build();
        return mapToResponse(departmentRepository.save(department));
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {
        return mapToResponse(findById(id));
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {
        Department department = findById(id);
        if (!department.getName().equals(request.getName()) && departmentRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Department already exists: " + request.getName());
        }
        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setLocation(request.getLocation());
        department.setPhone(request.getPhone());
        return mapToResponse(departmentRepository.save(department));
    }

    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.delete(findById(id));
    }

    @Override
    public void toggleDepartmentStatus(Long id) {
        Department department = findById(id);
        department.setIsActive(!Boolean.TRUE.equals(department.getIsActive()));
        departmentRepository.save(department);
    }

    private Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
    }

    private DepartmentResponse mapToResponse(Department d) {
        DepartmentResponse response = new DepartmentResponse();
        response.setId(d.getId());
        response.setName(d.getName());
        response.setDescription(d.getDescription());
        response.setLocation(d.getLocation());
        response.setPhone(d.getPhone());
        response.setTotalDoctors(d.getDoctors() != null ? d.getDoctors().size() : 0);
        response.setTotalRooms(d.getRooms() != null ? d.getRooms().size() : 0);
        response.setIsActive(d.getIsActive());
        response.setCreatedAt(d.getCreatedAt());
        return response;
    }
}
