package com.daw.cinemadaw.domain.cinema;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity()
public class Room {

    @Id //Indica que aquest atribut és la clau primària (PK)
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Genera automàticament l'ID al inserir un nou registre
    private Long id;
    @Column // si només posem column vol dir que aquest atribut (el de sota) és una columna, al costat del column podem posar el nom que volem per aquella columna
    private String name;
    @Column 
    private int capacity;


/**
 * RELACIÓ MOLTS A UN (Many-To-One)
 * --------------------------------------------------------------------
 * Indica que MOLTES instàncies d'aquesta classe (p.ex. Sales ) 
 * estan associades a UN sol Cinema.
 */
    @ManyToOne
// A nivell de Base de Dades, JPA crearà automàticament una columna 
// anomenada 'cinema_id' que actuarà com a Clau Forana (Foreign Key).
    private Cinema cinema;


    @OneToMany(mappedBy="room",cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Seat> seats= new ArrayList<>();

    public List<Seat> getSeats(){
        return seats;
    }

    public void setSeats(List<Seat>seats){
        this.seats=seats;
    }

    public Room() {   // aquest constructor és obligatori perque el framework el busca
    }

    public Room(int capacity, String name) {
        this.capacity = capacity;
        this.name = name;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCinema(Cinema cinema11) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Cinema getCinema() {
        return cinema;
    }

    









    







    

    











}
