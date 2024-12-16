package com.hotelApp.HotelBooking.services.roles;

import com.hotelApp.HotelBooking.dtos.RoleDto;
import com.hotelApp.HotelBooking.dtos.PaginatedResponseDto;

public interface RolesService {
    PaginatedResponseDto getRoles(int page, int pageSize, String sortField, String sortOrder);
    RoleDto createRole(RoleDto role);
    RoleDto updateRole(RoleDto roleDto);
    void deleteRole(Long id);
}
