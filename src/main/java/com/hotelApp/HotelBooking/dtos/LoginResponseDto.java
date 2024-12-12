package com.hotelApp.HotelBooking.dtos;

import lombok.Data;
import java.util.List;

@Data
public class LoginResponseDto {
    private String jwt;

    private UserDto user;

    private List<String> roles;

    private List<String> permissions;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
