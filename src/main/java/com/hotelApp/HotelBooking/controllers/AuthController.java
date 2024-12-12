package com.hotelApp.HotelBooking.controllers;

import com.hotelApp.HotelBooking.constants.Messages;
import com.hotelApp.HotelBooking.dtos.*;
import com.hotelApp.HotelBooking.entity.User;
import com.hotelApp.HotelBooking.repository.UserRepository;
import com.hotelApp.HotelBooking.services.auth.AuthService;
import com.hotelApp.HotelBooking.services.jwt.UserService;
import com.hotelApp.HotelBooking.utils.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public AuthController(
            AuthService authService,
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            JwtUtil jwtUtil,
            UserService userService
    ) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * API to register a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        try {
            UserDto userOutputDTO = authService.createUser(userDto);
            return new ResponseEntity<>(userOutputDTO, HttpStatus.CREATED);
        } catch (EntityExistsException entityExistsException) {
            var message = new MessageDto();
            message.setStatus(HttpStatus.NOT_ACCEPTABLE.toString());
            message.setMessage(Messages.USER_EXIST);
            return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception exception) {
            var message = new MessageDto();
            message.setStatus(HttpStatus.BAD_REQUEST.toString());
            message.setMessage(Messages.CREATE_FAILED);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * API to login user.
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
        } catch (BadCredentialsException badCredentialsException) {
            var message = new MessageDto();
            message.setStatus(HttpStatus.UNAUTHORIZED.toString());
            message.setMessage(Messages.AUTHENTICATION_FAILED);
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(loginRequestDto.getEmail());
        final String tokenJwt = jwtUtil.generateToken(userDetails);

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        Optional<User> userDb = userRepository.findByEmail(loginRequestDto.getEmail());

        if(userDb.isPresent()) {
            List<String> permissions = userService.getPermissionNamesByUserId(userDb.get().getId());
            List<String> roles = userService.getRoleNamesByUserId(userDb.get().getId());
            loginResponseDto.setJwt(tokenJwt);
            loginResponseDto.setUser(modelMapper.map(userDb.get(), UserDto.class));
            loginResponseDto.setRoles(roles);
            loginResponseDto.setPermissions(permissions);
        }

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }
}
