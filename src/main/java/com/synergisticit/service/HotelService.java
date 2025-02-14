package com.synergisticit.service;

import com.synergisticit.domain.Booking;
import com.synergisticit.domain.Hotel;
import com.synergisticit.domain.Room;
import com.synergisticit.repository.BookingRepository;
import com.synergisticit.repository.HotelRepository;
import com.synergisticit.repository.RoomRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public HotelService(HotelRepository hotelRepository,
                        RoomRepository roomRepository,
                        BookingRepository bookingRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }


    // Searches for hotels based on a variety of filters.
    public List<Hotel> searchHotels(String searchLocation,
                                    Integer noRooms,
                                    Integer noGuests,
                                    LocalDate checkInDate,
                                    LocalDate checkOutDate,
                                    Set<Integer> starRatings,
                                    Double maxPrice,
                                    Set<String> amenities) {

        Specification<Hotel> spec = Specification.where(null);

        // Smart Search: search hotel name, address, city, or state using the free text field.
        if (StringUtils.hasText(searchLocation)) {
            spec = spec.and((root, query, cb) ->
                    cb.or(
                            cb.like(cb.lower(root.get("name")), "%" + searchLocation.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("address")), "%" + searchLocation.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("city")), "%" + searchLocation.toLowerCase() + "%"),
                            cb.like(cb.lower(root.get("state")), "%" + searchLocation.toLowerCase() + "%")
                    )
            );
        }

        // Filter by star ratings if provided.
        if (starRatings != null && !starRatings.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.get("starRating").in(starRatings));
        }

        // Filter by price range if provided.
        if (maxPrice != null) {
            spec = spec.and((root, query, cb) -> cb.le(root.get("price"), maxPrice));
        }

        // Filter by amenities.
        if (amenities != null && !amenities.isEmpty()) {
            spec = spec.and((root, query, cb) -> root.join("amenities").in(amenities));
        }


        return hotelRepository.findAll(spec);
    }


     //Retrieves the list of room types available for a given hotel.
    public List<Room> getRoomsByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId);
    }


     // Books a room by saving a new Booking record.
    public Booking bookRoom(Booking booking) {
        return bookingRepository.save(booking);
    }
}
