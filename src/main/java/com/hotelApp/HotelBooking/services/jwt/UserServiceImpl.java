package com.hotelApp.HotelBooking.services.jwt;

import com.hotelApp.HotelBooking.constants.MessageConst;
import com.hotelApp.HotelBooking.dtos.UserDto;
import com.hotelApp.HotelBooking.entity.Permission;
import com.hotelApp.HotelBooking.entity.Role;
import com.hotelApp.HotelBooking.entity.User;
import com.hotelApp.HotelBooking.repository.PermissionRepository;
import com.hotelApp.HotelBooking.repository.RoleRepository;
import com.hotelApp.HotelBooking.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public UserServiceImpl(
            ModelMapper modelMapper,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PermissionRepository permissionRepository
    ) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<String> getPermissionNamesByUserId(Long userId) {
        Set<Permission> permissions = permissionRepository.findPermissionsByUserId(userId);
        return permissions.stream()
                .map(Permission::getName)
                .collect(Collectors.toList());
    }

    public List<String> getRoleNamesByUserId(Long userId) {
        Set<Role> roles = roleRepository.findRolesByUserId(userId);
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<UserDto> getUsers() {
        return userRepository.findAll().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(MessageConst.USER_NOT_FOUND));
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(!user.isEnabled())
                .build();
        return userDetails;
    }

    public UserDto getProfile(String username) {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(MessageConst.USER_NOT_FOUND));
        return modelMapper.map(user, UserDto.class);
    }
}
