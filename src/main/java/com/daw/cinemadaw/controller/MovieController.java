package com.daw.cinemadaw.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daw.cinemadaw.DTO.SeatsListDTO;
import com.daw.cinemadaw.domain.cinema.Movie;
import com.daw.cinemadaw.domain.cinema.Screening;
import com.daw.cinemadaw.domain.cinema.Seat;
import com.daw.cinemadaw.repository.MovieRepository;
import com.daw.cinemadaw.repository.ScreeningRepository;
import com.daw.cinemadaw.repository.SeatRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MovieController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private SeatRepository seatRepository;

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
                return "movies/editar-pelicules";
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


        @GetMapping("/client/pelicula/{id}")
        public String veureSessions(@PathVariable Long id, Model model){
            Movie movie = movieRepository.findById(id).orElse(null);
            List<Screening>sessions= screeningRepository.findByMovieId(id);
            model.addAttribute("movie",movie);
            model.addAttribute("sessions",sessions);

            return "client/sessions";
        }


        @GetMapping("/client/comprar/{id}")
        public String seleccionarSeients(@PathVariable Long id, Model model){
            Screening sessio = screeningRepository.findById(id).orElse(null);

            if(sessio==null){
                return "redirect:/movies";
            }
            model.addAttribute("screening",sessio);

            return "client/butaques";
        }


        @PostMapping("/client/reserva/confirmar")
        public String confirmarReserva(@RequestParam Long screeningId, @RequestParam List<Long>seientsSeleccionats, Model model){
            Screening screening = screeningRepository.findById(screeningId).orElse(null);
    
    // Recuperem els objectes Seat de la base de dades per mostrar-los al resum
    List<Seat> seientsObjectes = seatRepository.findAllById(seientsSeleccionats);

    // Marquem com a ocupats    //ELIMINAR
    for (Seat s : seientsObjectes) {
        s.setActive(false);
        seatRepository.save(s);
    }

    //ELIMINAR

    // Passem les dades al resum
    model.addAttribute("screening", screening);
    model.addAttribute("seients", seientsObjectes);

    return "client/resum-compra"; 
}

@GetMapping("/client/home")
public String home() {
    
    return "client/home";
}

// el que hi ha a la pissarra
 @GetMapping("/screenings/seats/{id}")
 public String selectSeats(@PathVariable Long id, Model model, HttpSession session){
     Optional<Screening>screening=screeningRepository.findById(id);
     if(screening.isEmpty()){
         return "redirect:/client/movies";

     }

     Map<Long,List<Long>> cart=(Map<Long,List<Long>>)session.getAttribute("cart");
     if(cart==null){
        cart=new HashMap<>();
     }

     SeatsListDTO seatsListDTO= new SeatsListDTO();
     seatsListDTO.setSeats(cart.get(id));
model.addAttribute("selectedSeats", seatsListDTO);
 model.addAttribute("screening",screening.get());
return "client/movies/sets";

 }


// el que hi ha a la pissarra
@PostMapping("/screenings/seats/confirm/{id}")
public String confirSeats(@PathVariable Long id, @ModelAttribute SeatsListDTO selectedSeats, Model model, HttpSession session){
    // obtenir mapa de la sessio o crear-lo

    Map<Long, List<Long>> cart=(Map<Long, List<Long>>) session.getAttribute("cart");

    if(cart==null){
        cart=new HashMap<>();
    }

    cart.put(id, selectedSeats.getSeats());
    session.setAttribute("cart",cart);
    System.out.println("cart actualitzat: "+cart);
    return "redirect:/client/movies/screenings/seats/"+id;
}
}
   
        
    
