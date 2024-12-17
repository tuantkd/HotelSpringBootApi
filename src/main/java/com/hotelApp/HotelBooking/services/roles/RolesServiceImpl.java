package com.hotelApp.HotelBooking.services.roles;

import com.hotelApp.HotelBooking.constants.MessageConst;
import com.hotelApp.HotelBooking.dtos.RoleDto;
import com.hotelApp.HotelBooking.dtos.PaginatedResponseDto;
import com.hotelApp.HotelBooking.entity.Role;
import com.hotelApp.HotelBooking.exception.CustomException;
import com.hotelApp.HotelBooking.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class RolesServiceImpl implements RolesService {
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public RolesServiceImpl(ModelMapper modelMapper, RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    public void roleNameExist(String name) {
        if (name != null) {
            roleRepository.findByName(name)
                    .ifPresent(p -> {
                        throw new CustomException(MessageFormat.format(MessageConst.ITEM_EXIST, name), HttpStatus.CONFLICT);
                    });
        }
    }

    public RoleDto findRoleById(Long id) {
        Role foundRole = roleRepository.findById(id)
                .orElseThrow(() -> new CustomException(MessageFormat.format(MessageConst.NOT_FOUND, id), HttpStatus.NOT_FOUND));
        return modelMapper.map(foundRole, RoleDto.class);
    }

    public RoleDto createRole(RoleDto roleDto) {
        roleNameExist(roleDto.getName());
        Role role = modelMapper.map(roleDto, Role.class);
        Role roleSaved = roleRepository.save(role);
        return modelMapper.map(roleSaved, RoleDto.class);
    }

    public RoleDto updateRole(RoleDto roleDto) {
        roleNameExist(roleDto.getName());
        Role existingRole = roleRepository.findById(roleDto.getId())
                .orElseThrow(() -> new CustomException(MessageFormat.format(MessageConst.NOT_FOUND, roleDto.getId()), HttpStatus.NOT_FOUND));

        modelMapper.map(roleDto, existingRole);
        Role roleUpdated = roleRepository.save(existingRole);
        return modelMapper.map(roleUpdated, RoleDto.class);
    }

    public void deleteRole(Long id) {
        Role foundRole = roleRepository.findById(id)
                .orElseThrow(() -> new CustomException(MessageFormat.format(MessageConst.NOT_FOUND, id), HttpStatus.NOT_FOUND));
        roleRepository.delete(foundRole);
    }

    public PaginatedResponseDto getRoles(int page, int pageSize, String sortField, String sortOrder) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<RoleDto> roles = roleRepository.findAll(pageable).map(role ->
                modelMapper.map(role, RoleDto.class));
        long totalAll = roleRepository.count(); // Total elements in the database

        return new PaginatedResponseDto(
                roles.getContent(),
                roles.getNumberOfElements(),
                roles.getTotalPages(),
                totalAll
        );
    }
}
