package com.synergisticit.repository;

import com.synergisticit.domain.Booking;
import org.springframework.data.jpa.repository.JpaRepository;




public interface BookingRepository extends JpaRepository<Booking, Long> {

}
