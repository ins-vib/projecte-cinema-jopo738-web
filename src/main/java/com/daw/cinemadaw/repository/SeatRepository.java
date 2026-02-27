package com.daw.cinemadaw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.cinemadaw.domain.cinema.Seat;
import com.daw.cinemadaw.domain.cinema.SeatType;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByType(SeatType type);
}
