package com.hotelApp.HotelBooking.dtos;

import lombok.Data;
import java.util.List;

@Data
public class PaginatedResponseDto<T> {
    private List<T> data;
    private long totalElements;
    private int totalPages;
    private long totalAll;

    public PaginatedResponseDto(List<T> data, long totalElements, int totalPages, long totalAll) {
        this.data = data;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.totalAll = totalAll;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
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
