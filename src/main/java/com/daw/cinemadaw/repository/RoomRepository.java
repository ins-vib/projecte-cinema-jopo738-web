package com.daw.cinemadaw.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.daw.cinemadaw.domain.cinema.Room;


@Repository  // Anotació per indicar que aquesta interfície és un repositori de Spring Data JPA, i que Spring Boot ha de crear una implementació d'aquesta interfície en temps d'execució.
public interface  RoomRepository extends JpaRepository<Room, Long> {


}
