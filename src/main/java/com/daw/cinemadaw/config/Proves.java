package com.daw.cinemadaw.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.daw.cinemadaw.domain.cinema.Cinema;
import com.daw.cinemadaw.repository.CinemaRepository;

@Component
public class Proves implements CommandLineRunner {   // es per provar que tot està funcionant 

    private CinemaRepository cinemaRepository; // la creem per poder treballar amb ella

    

    public Proves(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }



    @Override
    public void run (String... args) throws Exception{

        Cinema cinema1 = new Cinema("Ocine", "Gavarres, 46", "Tarragona", "431220");
        cinemaRepository.save(cinema1);
        
    }
}
