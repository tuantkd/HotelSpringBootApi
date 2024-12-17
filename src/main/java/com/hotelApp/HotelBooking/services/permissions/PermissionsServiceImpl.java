package com.hotelApp.HotelBooking.services.permissions;

import com.hotelApp.HotelBooking.constants.MessageConst;
import com.hotelApp.HotelBooking.dtos.PaginatedResponseDto;
import com.hotelApp.HotelBooking.dtos.PermissionDto;
import com.hotelApp.HotelBooking.dtos.RoleDto;
import com.hotelApp.HotelBooking.entity.Permission;
import com.hotelApp.HotelBooking.entity.Role;
import com.hotelApp.HotelBooking.exception.CustomException;
import com.hotelApp.HotelBooking.repository.PermissionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class PermissionsServiceImpl implements PermissionsService {
    private final ModelMapper modelMapper;
    private final PermissionRepository permissionRepository;

    public PermissionsServiceImpl(
            ModelMapper modelMapper,
            PermissionRepository permissionRepository
    ) {
        this.modelMapper = modelMapper;
        this.permissionRepository = permissionRepository;
    }

    public void permissionNameExist(String name) {
        if (name != null) {
            permissionRepository.findByName(name)
                    .ifPresent(p -> {
                        throw new CustomException(MessageFormat.format(MessageConst.ITEM_EXIST, name), HttpStatus.CONFLICT);
                    });
        }
    }

    public PermissionDto createPermission(PermissionDto permissionDto) {
        permissionNameExist(permissionDto.getName());
        Permission permission = modelMapper.map(permissionDto, Permission.class);
        Permission permissionSaved = permissionRepository.save(permission);
        return modelMapper.map(permissionSaved, PermissionDto.class);
    }

    public PermissionDto updatePermission(PermissionDto permissionDto) {
        permissionNameExist(permissionDto.getName());
        Permission existingPermission = permissionRepository.findById(permissionDto.getId())
                .orElseThrow(() -> new CustomException(MessageFormat.format(MessageConst.NOT_FOUND, permissionDto.getId()), HttpStatus.NOT_FOUND));

        modelMapper.map(permissionDto, existingPermission);
        Permission permissionUpdated = permissionRepository.save(existingPermission);
        return modelMapper.map(permissionUpdated, PermissionDto.class);
    }

    public PaginatedResponseDto getPermissions(int page, int pageSize, String sortField, String sortOrder) {
        Sort sort = Sort.by(sortOrder.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<PermissionDto> permissions = permissionRepository.findAll(pageable).map(permission ->
                modelMapper.map(permission, PermissionDto.class));
        long totalAll = permissionRepository.count(); // Total elements in the database

        return new PaginatedResponseDto(
                permissions.getContent(),
                permissions.getNumberOfElements(),
                permissions.getTotalPages(),
                totalAll
        );
    }
}
