package com.daw.cinemadaw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.daw.cinemadaw.domain.cinema.Movie;
import com.daw.cinemadaw.domain.cinema.Screening;
import com.daw.cinemadaw.repository.MovieRepository;
import com.daw.cinemadaw.repository.RoomRepository;
import com.daw.cinemadaw.repository.ScreeningRepository;

@Controller
public class ScreeningController {
    
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/screenings/new/{id}")
    public String mostrarFormulari(@PathVariable("id") Long movieId, Model model){
        Optional<Movie> movie =movieRepository.findById(movieId);
        if(movie.isPresent()){
            Screening screening = new Screening();
            screening.setMovie(movie.get());

            model.addAttribute("screening",screening);
            model.addAttribute("rooms",roomRepository.findAll());
            return "formulari-screening";
        }
        return "redirect:/movies";
    }

    @PostMapping("/screenings/save")
    public String save(@ModelAttribute Screening screening){
        screeningRepository.save(screening);
        return "redirect:/movies";
    }


}
