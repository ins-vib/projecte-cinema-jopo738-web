package com.daw.cinemadaw.domain.ticket;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.daw.cinemadaw.domain.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="comandes")
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;
    private double importTotal;
    private String nomClient;
    private String correuElectronic;
    private String estat;

    @ManyToOne
    private User user;

   @OneToMany(mappedBy="order",cascade=CascadeType.ALL)
    private List<Ticket> tickets= new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public double getImportTotal() {
        return importTotal;
    }

    public void setImportTotal(double importTotal) {
        this.importTotal = importTotal;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getCorreuElectronic() {
        return correuElectronic;
    }

    public void setCorreuElectronic(String correuElectronic) {
        this.correuElectronic = correuElectronic;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    



}
