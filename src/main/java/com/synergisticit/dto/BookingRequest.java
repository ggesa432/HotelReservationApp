package com.synergisticit.dto;

import java.time.LocalDate;

/**
 * @author GesangZeren
 * @project HotelReservation
 * @date 2/01/2025
 */
public class BookingRequest {
    private Long hotelId;
    private Long roomId;
    private int noOfRooms;
    private int noGuests;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String customerMobile;

    public int getNoGuests() {
        return noGuests;
    }

    public void setNoGuests(int noGuests) {
        this.noGuests = noGuests;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    // Getters and setters...
}
