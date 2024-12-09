package com.hotelApp.HotelBooking.mappers;

import com.hotelApp.HotelBooking.dtos.UserRequestDto;
import com.hotelApp.HotelBooking.dtos.UserResponseDto;
import com.hotelApp.HotelBooking.entity.User;

public interface UserMapperInterface {
    User toEntity(UserRequestDto dto);
    UserResponseDto toDto(User user);
}
