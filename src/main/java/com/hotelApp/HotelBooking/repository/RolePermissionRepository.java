package com.hotelApp.HotelBooking.repository;

import com.hotelApp.HotelBooking.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}