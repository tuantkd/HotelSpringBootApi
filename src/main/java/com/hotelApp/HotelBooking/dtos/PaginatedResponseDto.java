package com.hotelApp.HotelBooking.dtos;

import lombok.Data;
import java.util.List;

@Data
public class PaginatedResponseDto {
    private List<RoleDto> roles;
    private long totalElements;
    private int totalPages;
    private long totalAll;

    public PaginatedResponseDto(List<RoleDto> roles, long totalElements, int totalPages, long totalAll) {
        this.roles = roles;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.totalAll = totalAll;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalAll() {
        return totalAll;
    }

    public void setTotalAll(long totalAll) {
        this.totalAll = totalAll;
    }
}
