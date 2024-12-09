package com.hotelApp.HotelBooking.mappers;

import com.hotelApp.HotelBooking.dtos.UserRequestDto;
import com.hotelApp.HotelBooking.dtos.UserResponseDto;
import com.hotelApp.HotelBooking.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements UserMapperInterface {
    // Map Input DTO to Entity
    public User toEntity(UserRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setUserRole(dto.getUserRole());
        return user;
    }

    // Map Entity to Output DTO
    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setName(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setUserRole(user.getUserRole());
        return dto;
    }
}
