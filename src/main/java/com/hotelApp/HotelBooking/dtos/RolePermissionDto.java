package com.hotelApp.HotelBooking.dtos;

import com.hotelApp.HotelBooking.entity.Permission;
import com.hotelApp.HotelBooking.entity.Role;
import lombok.Data;

@Data
public class RolePermissionDto {
    private Long id;

    private Role role;

    private Permission permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
