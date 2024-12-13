package com.hotelApp.HotelBooking.controllers;

import com.hotelApp.HotelBooking.constants.MessageConst;
import com.hotelApp.HotelBooking.dtos.*;
import com.hotelApp.HotelBooking.services.auth.AuthService;
import com.hotelApp.HotelBooking.services.jwt.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    public AuthController(
            AuthService authService,
            UserService userService
    ) {
        this.authService = authService;
        this.userService = userService;
    }

    /** API to register a new user. */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        MessageDto message = new MessageDto();
        try {
            UserDto userOutputDTO = authService.createUser(userDto);
            return new ResponseEntity<>(userOutputDTO, HttpStatus.CREATED);
        } catch (EntityExistsException entityExistsException) {
            message.setStatus(HttpStatus.NOT_ACCEPTABLE.toString());
            message.setMessage(MessageConst.USER_EXIST);
            return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            message.setStatus(HttpStatus.BAD_REQUEST.toString());
            message.setMessage(MessageConst.CREATE_FAILED);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    /** API to generate Token for user. */
    @PostMapping("/login")
    public ResponseEntity<?> getAuthenticationToken(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            LoginResponseDto loginResponseDto = authService.getAuthenticationToken(loginRequestDto);
            return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
        } catch (BadCredentialsException badCredentialsException) {
            MessageDto message = new MessageDto();
            message.setStatus(HttpStatus.UNAUTHORIZED.toString());
            message.setMessage(MessageConst.IN_CORRECT);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }
}
