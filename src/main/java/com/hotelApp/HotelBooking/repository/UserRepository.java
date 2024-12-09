package com.hotelApp.HotelBooking.repository;

import com.hotelApp.HotelBooking.entity.User;
import com.hotelApp.HotelBooking.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserRole(UserRole userRole);
}
