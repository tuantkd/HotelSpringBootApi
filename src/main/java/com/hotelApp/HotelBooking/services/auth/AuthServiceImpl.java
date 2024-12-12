package com.hotelApp.HotelBooking.services.auth;

import com.hotelApp.HotelBooking.constants.Messages;
import com.hotelApp.HotelBooking.dtos.UserDto;
import com.hotelApp.HotelBooking.entity.*;
import com.hotelApp.HotelBooking.repository.*;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AuthServiceImpl(
        UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new EntityExistsException(Messages.EMAIL_EXIST + userRequestDto.getEmail());
        }

        User user = modelMapper.map(userRequestDto, User.class);
        user.setPassword(new BCryptPasswordEncoder().encode(userRequestDto.getPassword()));
        var savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }
}
