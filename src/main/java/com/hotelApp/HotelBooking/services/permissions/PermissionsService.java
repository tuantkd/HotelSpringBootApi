package com.hotelApp.HotelBooking.services.permissions;

import com.hotelApp.HotelBooking.dtos.PaginatedResponseDto;
import com.hotelApp.HotelBooking.dtos.PermissionDto;

public interface PermissionsService {
    PermissionDto createPermission(PermissionDto role);

    PermissionDto updatePermission(PermissionDto permissionDto);

    PaginatedResponseDto getPermissions(int page, int pageSize, String sortField, String sortOrder);

    void deletePermission(Long id);

    PermissionDto findPermissionById(Long id);
}
