package com.hotelApp.HotelBooking.services.auth;

import com.hotelApp.HotelBooking.dtos.UserRequestDto;
import com.hotelApp.HotelBooking.dtos.UserResponseDto;

public interface AuthService {
    void createAdminAccount();
    UserResponseDto createUser(UserRequestDto userRequestDto);
}
