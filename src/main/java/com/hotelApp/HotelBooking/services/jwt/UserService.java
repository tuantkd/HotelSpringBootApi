package com.hotelApp.HotelBooking.services.jwt;

import com.hotelApp.HotelBooking.dtos.RoleDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService {
    UserDetailsService userDetailsService ();
    List<String> getPermissionNamesByUserId(Long userId);
    List<String> getRoleNamesByUserId(Long userId);
}
