package com.hotelApp.HotelBooking.services.jwt;

import com.hotelApp.HotelBooking.constants.Messages;
import com.hotelApp.HotelBooking.dtos.UserDto;
import com.hotelApp.HotelBooking.entity.Permission;
import com.hotelApp.HotelBooking.entity.Role;
import com.hotelApp.HotelBooking.repository.PermissionRepository;
import com.hotelApp.HotelBooking.repository.RoleRepository;
import com.hotelApp.HotelBooking.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public UserServiceImpl(
        UserRepository userRepository,
        RoleRepository roleRepository,
        PermissionRepository permissionRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public UserDetailsService userDetailsService () {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findByEmail(username).orElseThrow(()
                        -> new UsernameNotFoundException(Messages.USER_NOT_FOUND));
            }
        };
    }

    public List<String> getPermissionNamesByUserId(Long userId) {
        // Fetch permissions by user ID
        Set<Permission> permissions = permissionRepository.findPermissionsByUserId(userId);

        // Convert permissions to their names using Streams
        return permissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
    }

    public List<String> getRoleNamesByUserId(Long userId) {
        // Fetch roles by user ID
        Set<Role> roles = roleRepository.findRolesByUserId(userId);

        // Convert roles to their names using Streams
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
