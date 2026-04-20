package com.daw.cinemadaw.service;

import java.util.List;
import java.util.Map;

import com.daw.cinemadaw.domain.ticket.Order;
import com.daw.cinemadaw.repository.ScreeningRepository;
import com.daw.cinemadaw.repository.SeatRepository;
import com.daw.cinemadaw.repository.TicketRepository;

import jakarta.transaction.Transactional;

public class TicketService {
    
    private final ScreeningRepository screeningRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    
    
    public TicketService(ScreeningRepository screeningRepository, SeatRepository seatRepository,
            TicketRepository ticketRepository) {
        this.screeningRepository = screeningRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Order crearOrderTickets(Map<Long, List<Long>> cart){
        Order order = new Order();
        //intruccions per crear l'order a partir del cart
        //per cada screening i llista de seatsIds al cart:
        //1. recuperar la screening i els seients corresponents
        //2. crear els tickets associats a l'order
        //3. guardar l'order i els tickets a la base de dadese
        // només instruccions Base de dades per crear una transacció

        return order;
    }

    
}
