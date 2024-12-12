package com.hotelApp.HotelBooking.repository;

import com.hotelApp.HotelBooking.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}
