package com.hotelApp.HotelBooking.controllers;

import com.hotelApp.HotelBooking.constants.MessageConst;
import com.hotelApp.HotelBooking.dtos.MessageDto;
import com.hotelApp.HotelBooking.dtos.UserDto;
import com.hotelApp.HotelBooking.services.jwt.UserService;
import com.hotelApp.HotelBooking.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AdminController(
            JwtUtil jwtUtil,
            UserService userService
    ) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /** API to get all user. */
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        try {
            List<UserDto> userDtoList = userService.getUsers();
            return new ResponseEntity<>(userDtoList, HttpStatus.OK);
        } catch (Exception exception) {
            var message = new MessageDto();
            message.setStatus(HttpStatus.BAD_REQUEST.toString());
            message.setMessage(MessageConst.USER_NOT_FOUND);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    /** API to profile user details */
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        MessageDto message = new MessageDto();
        try {
            if (token != null || token.startsWith("Bearer ")) {
                String jwtToken = token.substring(7);
                if(jwtUtil.isTokenValid(jwtToken)){
                    String username = jwtUtil.extractUsername(jwtToken);
                    return new ResponseEntity<>(userService.getProfile(username), HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            message.setStatus(HttpStatus.FORBIDDEN.toString());
            message.setMessage(MessageConst.INVALID_TOKEN);
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
        message.setStatus(HttpStatus.UNAUTHORIZED.toString());
        message.setMessage(MessageConst.AUTHORIZATION_FAILED);
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
}
