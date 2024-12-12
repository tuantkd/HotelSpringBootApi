package com.hotelApp.HotelBooking.repository;

import com.hotelApp.HotelBooking.entity.Permission;
import com.hotelApp.HotelBooking.entity.Role;
import com.hotelApp.HotelBooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
