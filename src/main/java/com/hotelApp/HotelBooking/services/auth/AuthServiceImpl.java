package com.hotelApp.HotelBooking.services.auth;

import com.hotelApp.HotelBooking.constants.Messages;
import com.hotelApp.HotelBooking.dtos.UserRequestDto;
import com.hotelApp.HotelBooking.dtos.UserResponseDto;
import com.hotelApp.HotelBooking.entity.User;
import com.hotelApp.HotelBooking.enums.UserRole;
import com.hotelApp.HotelBooking.mappers.UserMapper;
import com.hotelApp.HotelBooking.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @PostConstruct
    public void createAdminAccount() {
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount.isEmpty()) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("Admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(user);
            System.out.println(Messages.CREATE_SUCCESS);
        } else {
            System.out.println(Messages.ACCOUNT_EXIST);
        }
    }

    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new EntityExistsException(Messages.EMAIL_EXIST + userRequestDto.getEmail());
        }

        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(new BCryptPasswordEncoder().encode(userRequestDto.getPassword())); // Encrypt password
        user.setUserRole(UserRole.CUSTOMER);
        var savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
