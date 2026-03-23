package com.hospital.admin.repository;

import com.hospital.admin.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    boolean existsByRoomNumber(String roomNumber);
    List<Room> findByDepartmentId(Long departmentId);
    List<Room> findByRoomType(String roomType);
    List<Room> findByIsActiveTrue();
}
