package com.hotelApp.HotelBooking.dtos;

import lombok.Data;
import java.util.List;

@Data
public class LoginResponseDto {
    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
