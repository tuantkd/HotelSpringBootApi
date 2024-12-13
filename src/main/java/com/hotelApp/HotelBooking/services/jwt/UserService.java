package com.hotelApp.HotelBooking.services.jwt;

import com.hotelApp.HotelBooking.dtos.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService {
    UserDetailsService userDetailsService ();
    List<String> getPermissionNamesByUserId(Long userId);
    List<String> getRoleNamesByUserId(Long userId);
    List<UserDto> getUsers();
}
