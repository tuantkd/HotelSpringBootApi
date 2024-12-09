package com.hotelApp.HotelBooking.services;

import com.hotelApp.HotelBooking.dtos.UserRequestDto;
import com.hotelApp.HotelBooking.dtos.UserResponseDto;

public interface AuthServiceInterface {
    void createAdminAccount();
    UserResponseDto createUser(UserRequestDto userRequestDto);
}
