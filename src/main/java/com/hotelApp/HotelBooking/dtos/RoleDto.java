package com.hotelApp.HotelBooking.dtos;

import com.hotelApp.HotelBooking.entity.Permission;
import lombok.Data;

import java.util.Set;

@Data
public class RoleDto {
    private Long id;

    private String name;

    private String description;

    private Set<PermissionDto> permissions;

    public Set<PermissionDto> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionDto> permissions) {
        this.permissions = permissions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
