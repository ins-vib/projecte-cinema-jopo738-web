package com.daw.cinemadaw.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.daw.cinemadaw.domain.cinema.Movie;
import com.daw.cinemadaw.repository.MovieRepository;

@Controller
public class MovieController {

    private MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping("/movies")
    public String movies(Model model){

        // 1. Recuperem la llista de tots els cinemes de la base de dades
    // fent servir el repositori que hem injectat abans.
        List<Movie> movies = movieRepository.findAll();
        // 2. Afegim la llista al model. 
    // "llista" és el nom (clau) que farem servir al fitxer HTML (Thymeleaf).
    // "cinemes" és la variable Java que conté les dades reals.
        model.addAttribute("llista",movies);  // dades que li passem al model vista


        // Retorna el nom de la vista que s'ha de mostrar a l'usuari.
        // En aquest cas, Spring buscarà un fitxer anomenat "cinemes.html"
        return "movies/pelicules";
    }

    @GetMapping("/pelicula/create")
    public String create_movies(Model model){

       Movie movie = new Movie();    // Tots els valors del formulari sorten amb blanc perque hem creat un nou cinema
            
            model.addAttribute("pelicula", movie);
            
        return "movies/create-pelicules";
    }

    @PostMapping("/pelicula/create")
    public String guardarpelicula(@ModelAttribute("pelicula") Movie movie){
        movieRepository.save(movie);
        return "redirect:/movies";
    }
    
}
