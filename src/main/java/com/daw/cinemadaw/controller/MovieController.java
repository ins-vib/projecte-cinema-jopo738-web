package com.daw.cinemadaw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.daw.cinemadaw.domain.cinema.Movie;
import com.daw.cinemadaw.repository.MovieRepository;

import jakarta.validation.Valid;

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
    public String guardarpelicula(@Valid @ModelAttribute("pelicula") Movie movie,BindingResult result, Model model){

        if(result.hasErrors()){
            return "movies/create-pelicules";
        }
        movieRepository.save(movie);
        return "redirect:/movies";
    }


    @GetMapping("/pelicula/{id}")
        public String detall(@PathVariable Long id, Model model){

    
            Optional<Movie> optional=movieRepository.findById(id);
            if(optional.isPresent()){
                Movie pelicula= optional.get();
                model.addAttribute("pelicula", pelicula);
                return "movies/detall-pelicules";
            }
           return "redirect:/";
        }

        @GetMapping("/pelicula/update/{id}")
        public String mostrarFormulariEditar(@PathVariable Long id, Model model){

            Optional<Movie> optional = movieRepository.findById(id);
            if(optional.isPresent()){
                Movie pelicula = optional.get();
                model.addAttribute("pelicula",pelicula);
                 return "movies/editar-pelicules";
            }
            return "redirect:/movies";

            
           
        }
        
        @PostMapping("/pelicula/edit")
        public String editCinema(@Valid @ModelAttribute("pelicula") Movie pelicula, BindingResult result,Model model){


            if(result.hasErrors()){
                return "movies/create-pelicules";
            }
            movieRepository.save(pelicula);  // serveix per desar un nou i desar un actualitzat, crea un nou si no posem identificador
            return "redirect:/movies";
        }

        //delete
        @GetMapping("/pelicula/delete/{id}")
        public String delete(@PathVariable Long id){

            Optional<Movie> optional=movieRepository.findById(id);
            if(optional.isPresent()){
                Movie pelicula= optional.get();
                movieRepository.deleteById(id);     
            } 

            return "redirect:/movies";
        }
    
}
