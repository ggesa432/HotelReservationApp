package com.synergisticit.service;

import com.synergisticit.domain.Booking;
import com.synergisticit.domain.Hotel;
import com.synergisticit.domain.Room;
import com.synergisticit.dto.BookingRequest;
import com.synergisticit.repository.BookingRepository;
import com.synergisticit.repository.HotelRepository;
import com.synergisticit.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

/**
 * @author GesangZeren
 * @project HotelReservation
 * @date 2/11/2025
 */
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public BookingService(BookingRepository bookingRepository,
                          HotelRepository hotelRepository,
                          RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }


     // Creates a provisional booking (booking intent) from the provided booking request.

    public Booking createBookingIntent(BookingRequest bookingRequest) {
        // Retrieve the hotel and room from the database.
        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found with id: " + bookingRequest.getHotelId()));
        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found with id: " + bookingRequest.getRoomId()));

        Booking booking = new Booking();
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setNoRooms(bookingRequest.getNoOfRooms());
        booking.setNoGuests(bookingRequest.getNoGuests());
        booking.setCheckInDate(bookingRequest.getCheckInDate());
        booking.setCheckOutDate(bookingRequest.getCheckOutDate());
        booking.setCustomerMobile(bookingRequest.getCustomerMobile());

        // Calculate the number of nights between check-in and check-out.
        long nights = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate());

        // Calculate total price: room price * number of rooms * number of nights.
        double totalPrice = room.getRoomPrice() * bookingRequest.getNoOfRooms() * nights;
        booking.setTotalPrice(totalPrice);

        // Set discount if applicable (for simplicity, set it to 0 here).
        booking.setDiscount(0.0);


        return bookingRepository.save(booking);
    }
}
