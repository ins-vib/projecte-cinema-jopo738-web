package com.daw.cinemadaw.domain.cinema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity()
public class Room {

    @Id //Indica que aquest atribut és la clau primària (PK)
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Genera automàticament l'ID al inserir un nou registre
    private Long id;
    @Column // si només posem column vol dir que aquest atribut (el de sota) és una columna, al costat del column podem posar el nom que volem per aquella columna
    private String name;
    @Column 
    private int capacity;

    public Room() {   // aquest constructor és obligatori perque el framework el busca
    }

    

    











}
