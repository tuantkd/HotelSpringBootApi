package com.hotelApp.HotelBooking.dtos;

import com.hotelApp.HotelBooking.enums.UserRole;
import lombok.Data;

@Data
public class UserRequestDto {
    private String name;

    private String email;

    private String password;

    private UserRole userRole;

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
