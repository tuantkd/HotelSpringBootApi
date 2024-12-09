package com.hotelApp.HotelBooking.controllers;

import com.hotelApp.HotelBooking.constants.Messages;
import com.hotelApp.HotelBooking.dtos.UserRequestDto;
import com.hotelApp.HotelBooking.dtos.UserResponseDto;
import com.hotelApp.HotelBooking.services.AuthServiceInterface;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthServiceInterface authService;

    public AuthController(AuthServiceInterface authService) {
        this.authService = authService;
    }

    /**
     * API to register a new user.
     *
     * @param userRequestDto - User input data.
     * @return - User output data with success message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody UserRequestDto userRequestDto) {
        try {
            UserResponseDto userOutputDTO = authService.createUser(userRequestDto);
            return new ResponseEntity<>(userOutputDTO, HttpStatus.CREATED);
        } catch (EntityExistsException entityExistsException) {
            return new ResponseEntity<>(Messages.USER_EXIST, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            return new ResponseEntity<>(Messages.CREATE_FAILED, HttpStatus.BAD_REQUEST);
        }
    }
}
