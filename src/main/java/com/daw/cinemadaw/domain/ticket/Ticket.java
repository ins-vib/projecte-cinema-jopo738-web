package com.daw.cinemadaw.domain.ticket;

import com.daw.cinemadaw.domain.cinema.Screening;
import com.daw.cinemadaw.domain.cinema.Seat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Double preu;

    @ManyToOne
    private Order order;

    @ManyToOne
    private Screening screening;

    @ManyToOne
    private Seat seat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPreu() {
        return preu;
    }

    public void setPreu(Double preu) {
        this.preu = preu;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    
}
