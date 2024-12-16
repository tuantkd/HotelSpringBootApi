package com.hotelApp.HotelBooking.services.auth;

import com.hotelApp.HotelBooking.constants.MessageConst;
import com.hotelApp.HotelBooking.dtos.LoginRequestDto;
import com.hotelApp.HotelBooking.dtos.LoginResponseDto;
import com.hotelApp.HotelBooking.dtos.UserDto;
import com.hotelApp.HotelBooking.entity.*;
import com.hotelApp.HotelBooking.repository.*;
import com.hotelApp.HotelBooking.utils.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil,
            ModelMapper modelMapper,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }

    public UserDto createUser(UserDto userRequestDto) {
        User user = modelMapper.map(userRequestDto, User.class);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        var savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    public LoginResponseDto getAuthenticationToken(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getEmail(),
                        loginRequestDto.getPassword()
                )
        );
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        if (authentication.isAuthenticated()) {
            loginResponseDto.setJwtToken(jwtUtil.generateToken(loginRequestDto.getEmail()));
        }
        return loginResponseDto;
    }
}
