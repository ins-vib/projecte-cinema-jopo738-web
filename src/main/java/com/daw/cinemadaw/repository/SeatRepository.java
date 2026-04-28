package com.daw.cinemadaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.daw.cinemadaw.domain.cinema.Seat;

import jakarta.transaction.Transactional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Modifying
@Transactional
@Query("DELETE FROM Seat s WHERE s.room.id = :roomId")
void deleteByRoomId(@Param("roomId") Long roomId);
}
