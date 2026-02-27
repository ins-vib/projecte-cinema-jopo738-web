package com.daw.cinemadaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.cinemadaw.domain.cinema.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    
}
