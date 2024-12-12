package com.hotelApp.HotelBooking.services.auth;

import com.hotelApp.HotelBooking.dtos.UserDto;

public interface AuthService {
    UserDto createUser(UserDto userRequestDto);
}
