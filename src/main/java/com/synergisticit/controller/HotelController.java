package com.synergisticit.controller;

import com.synergisticit.domain.Booking;
import com.synergisticit.domain.Hotel;
import com.synergisticit.domain.Room;
import com.synergisticit.service.HotelService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/hotels")
public class HotelController {

    private final HotelService hotelService;

    // Constructor injection of the HotelService
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

     //Endpoint to search for hotels using various filters.
    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestParam(required = false) String searchLocation,
            @RequestParam(required = false) Integer noRooms,
            @RequestParam(required = false) Integer noGuests,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(required = false) Set<Integer> starRatings,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Set<String> amenities) {

        List<Hotel> hotels = hotelService.searchHotels(
                searchLocation, noRooms, noGuests, checkInDate, checkOutDate, starRatings, maxPrice, amenities);
        return ResponseEntity.ok(hotels);
    }

     //Endpoint to retrieve all available room types for a given hotel.
    @GetMapping("/{hotelId}/rooms")
    public ResponseEntity<List<Room>> getRoomsByHotel(@PathVariable Long hotelId) {
        List<Room> rooms = hotelService.getRoomsByHotelId(hotelId);
        return ResponseEntity.ok(rooms);
    }

     //Endpoint to create a booking.
    @PostMapping("/booking")
    public ResponseEntity<Booking> bookRoom(@RequestBody Booking booking) {
        Booking savedBooking = hotelService.bookRoom(booking);
        return ResponseEntity.ok(savedBooking);
    }
}
