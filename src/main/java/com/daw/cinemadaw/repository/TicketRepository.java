package com.daw.cinemadaw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.daw.cinemadaw.domain.ticket.Ticket;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
    List<Ticket>findByScreeningId(Long screeningId);

    List<Ticket> findByUserId(Long userId);
}
