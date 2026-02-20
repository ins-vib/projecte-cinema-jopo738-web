package com.daw.cinemadaw.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.cinemadaw.domain.cinema.Cinema;

@Repository  // Anotació per indicar que aquesta interfície és un repositori de Spring Data JPA, i que Spring Boot ha de crear una implementació d'aquesta interfície en temps d'execució.
public interface  CinemaRepository extends JpaRepository<Cinema, Long> {
 // Aquesta interfície hereta de JpaRepository, que proporciona mètodes bàsics per a operacions CRUD (Create, Read, Update, Delete) sobre l'entitat Cinema.
 // El primer paràmetre (Cinema) és el tipus d'entitat que gestiona aquest repositori, i el segon paràmetre (Long) és el tipus de l'identificador de l'entitat.

// "Torna'm una LLISTA de CINEMES que estiguin en aquesta CIUTAT"
   List<Cinema> findByCity(String city);

}
