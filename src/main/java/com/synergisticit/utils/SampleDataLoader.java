package com.synergisticit.utils;

import com.synergisticit.domain.Hotel;
import com.synergisticit.domain.Room;
import com.synergisticit.repository.HotelRepository;
import com.synergisticit.repository.RoomRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;


@Component
public class SampleDataLoader implements CommandLineRunner {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    public SampleDataLoader(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
//        roomRepository.deleteAll();
//        hotelRepository.deleteAll();
        // Check if sample data already exists by counting hotels.
        if (hotelRepository.count() > 0) {
            System.out.println("Sample data already exists. Skipping data load.");
            return;
        }

        // Hotel 1: Grand Plaza Hotel in New York
        Hotel hotel1 = new Hotel();
        hotel1.setName("Grand Plaza Hotel");
        hotel1.setAddress("123 Main St");
        hotel1.setCity("New York");
        hotel1.setState("NY");
        hotel1.setStarRating(5);
        hotel1.setPrice(350.00);
        Set<String> amenities1 = new HashSet<>();
        amenities1.add("BREAKFAST");
        amenities1.add("FITNESS CENTER");
        amenities1.add("PARKING");
        hotel1.setAmenities(amenities1);
        hotel1 = hotelRepository.save(hotel1);

        Room room1 = new Room();
        room1.setHotel(hotel1);
        room1.setRoomType("Suite");
        room1.setAvailableRooms(5);
        room1.setRoomPrice(400.00);
        roomRepository.save(room1);

        Room room2 = new Room();
        room2.setHotel(hotel1);
        room2.setRoomType("Standard");
        room2.setAvailableRooms(10);
        room2.setRoomPrice(250.00);
        roomRepository.save(room2);

        // Hotel 2: City Lights Inn in New York
        Hotel hotel2 = new Hotel();
        hotel2.setName("City Lights Inn");
        hotel2.setAddress("456 Broadway");
        hotel2.setCity("New York");
        hotel2.setState("NY");
        hotel2.setStarRating(4);
        hotel2.setPrice(200.00);
        Set<String> amenities2 = new HashSet<>();
        amenities2.add("BAR OR LOUNGE");
        amenities2.add("PARKING");
        hotel2.setAmenities(amenities2);
        hotel2 = hotelRepository.save(hotel2);

        Room room3 = new Room();
        room3.setHotel(hotel2);
        room3.setRoomType("Deluxe");
        room3.setAvailableRooms(3);
        room3.setRoomPrice(300.00);
        roomRepository.save(room3);

        // Hotel 3: Ocean View Resort in Miami
        Hotel hotel3 = new Hotel();
        hotel3.setName("Ocean View Resort");
        hotel3.setAddress("789 Ocean Dr");
        hotel3.setCity("Miami");
        hotel3.setState("FL");
        hotel3.setStarRating(5);
        hotel3.setPrice(500.00);
        Set<String> amenities3 = new HashSet<>();
        amenities3.add("POOL");
        amenities3.add("BREAKFAST");
        amenities3.add("SPA");
        hotel3.setAmenities(amenities3);
        hotel3 = hotelRepository.save(hotel3);

        Room room4 = new Room();
        room4.setHotel(hotel3);
        room4.setRoomType("Oceanfront Suite");
        room4.setAvailableRooms(4);
        room4.setRoomPrice(600.00);
        roomRepository.save(room4);

        Room room5 = new Room();
        room5.setHotel(hotel3);
        room5.setRoomType("Standard");
        room5.setAvailableRooms(8);
        room5.setRoomPrice(350.00);
        roomRepository.save(room5);

        // Hotel 4: Mountain Retreat in Denver
        Hotel hotel4 = new Hotel();
        hotel4.setName("Mountain Retreat");
        hotel4.setAddress("321 Alpine Rd");
        hotel4.setCity("Denver");
        hotel4.setState("CO");
        hotel4.setStarRating(4);
        hotel4.setPrice(250.00);
        Set<String> amenities4 = new HashSet<>();
        amenities4.add("BREAKFAST");
        amenities4.add("FIREPLACE");
        amenities4.add("GYM");
        hotel4.setAmenities(amenities4);
        hotel4 = hotelRepository.save(hotel4);

        Room room6 = new Room();
        room6.setHotel(hotel4);
        room6.setRoomType("Cabin");
        room6.setAvailableRooms(2);
        room6.setRoomPrice(300.00);
        roomRepository.save(room6);

        Room room7 = new Room();
        room7.setHotel(hotel4);
        room7.setRoomType("Standard");
        room7.setAvailableRooms(5);
        room7.setRoomPrice(200.00);
        roomRepository.save(room7);

        // Hotel 5: Urban Stay in Chicago
        Hotel hotel5 = new Hotel();
        hotel5.setName("Urban Stay");
        hotel5.setAddress("654 City Center");
        hotel5.setCity("Chicago");
        hotel5.setState("IL");
        hotel5.setStarRating(3);
        hotel5.setPrice(150.00);
        Set<String> amenities5 = new HashSet<>();
        amenities5.add("BREAKFAST");
        amenities5.add("PARKING");
        hotel5.setAmenities(amenities5);
        hotel5 = hotelRepository.save(hotel5);

        Room room8 = new Room();
        room8.setHotel(hotel5);
        room8.setRoomType("Economy");
        room8.setAvailableRooms(10);
        room8.setRoomPrice(120.00);
        roomRepository.save(room8);

        // Hotel 6: Luxury Suites in Los Angeles
        Hotel hotel6 = new Hotel();
        hotel6.setName("Luxury Suites");
        hotel6.setAddress("987 Sunset Blvd");
        hotel6.setCity("Los Angeles");
        hotel6.setState("CA");
        hotel6.setStarRating(5);
        hotel6.setPrice(800.00);
        Set<String> amenities6 = new HashSet<>();
        amenities6.add("BREAKFAST");
        amenities6.add("POOL");
        amenities6.add("SPA");
        amenities6.add("GYM");
        hotel6.setAmenities(amenities6);
        hotel6 = hotelRepository.save(hotel6);

        Room room9 = new Room();
        room9.setHotel(hotel6);
        room9.setRoomType("Executive Suite");
        room9.setAvailableRooms(3);
        room9.setRoomPrice(1000.00);
        roomRepository.save(room9);

        // Hotel 7: Budget Inn in Dallas
        Hotel hotel7 = new Hotel();
        hotel7.setName("Budget Inn");
        hotel7.setAddress("321 Elm St");
        hotel7.setCity("Dallas");
        hotel7.setState("TX");
        hotel7.setStarRating(2);
        hotel7.setPrice(80.00);
        Set<String> amenities7 = new HashSet<>();
        amenities7.add("PARKING");
        amenities7.add("BREAKFAST");
        hotel7.setAmenities(amenities7);
        hotel7 = hotelRepository.save(hotel7);

        Room room10 = new Room();
        room10.setHotel(hotel7);
        room10.setRoomType("Standard");
        room10.setAvailableRooms(20);
        room10.setRoomPrice(80.00);
        roomRepository.save(room10);

        // Hotel 8: Boutique Hotel in San Francisco
        Hotel hotel8 = new Hotel();
        hotel8.setName("Boutique Hotel");
        hotel8.setAddress("456 Market St");
        hotel8.setCity("San Francisco");
        hotel8.setState("CA");
        hotel8.setStarRating(4);
        hotel8.setPrice(300.00);
        Set<String> amenities8 = new HashSet<>();
        amenities8.add("BREAKFAST");
        amenities8.add("GYM");
        amenities8.add("PARKING");
        hotel8.setAmenities(amenities8);
        hotel8 = hotelRepository.save(hotel8);

        Room room11 = new Room();
        room11.setHotel(hotel8);
        room11.setRoomType("Deluxe");
        room11.setAvailableRooms(5);
        room11.setRoomPrice(350.00);
        roomRepository.save(room11);

        System.out.println("Sample data loaded successfully.");
    }
}