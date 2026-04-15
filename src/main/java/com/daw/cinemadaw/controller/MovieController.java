package com.daw.cinemadaw.controller;

import java.util.ArrayList;
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
        public String seleccionarSeients(@PathVariable Long id, Model model, HttpSession session){
            // 1. Busquem la sessió de cinema
    Optional<Screening> screening = screeningRepository.findById(id);
    
    if (screening.isEmpty()) {
        return "redirect:/movies";
    }

    // 2. Intentem recuperar el "cart" (carret) de la sessió HTTP
    Map<Long, List<Long>> cart = (Map<Long, List<Long>>) session.getAttribute("cart");
    if (cart == null) {
        cart = new HashMap<>();
    }

    // 3. Creem un DTO per passar els seients ja seleccionats a la vista
    // Nota: Assegura't de tenir creada la classe SeatsListDTO
    SeatsListDTO seatsListDTO = new SeatsListDTO();
    
    // Si aquest ID de sessió ja existeix al mapa, n'agafem els seients
    if (cart.containsKey(id)) {
        seatsListDTO.setSeats(cart.get(id));
    }

    // 4. Afegim tot al model per a Thymeleaf
    model.addAttribute("selectedSeats", seatsListDTO);
    model.addAttribute("screening", screening.get());

    return "client/butaques";
        }


        @PostMapping("/client/reserva/confirmar")
        public String confirmarReserva(@RequestParam Long screeningId, @RequestParam List<Long>seientsSeleccionats, Model model, HttpSession session){
            // 1. Obtenir el mapa "cart" de la sessió o crear-lo si no existeix
    Map<Long, List<Long>> cart = (Map<Long, List<Long>>) session.getAttribute("cart");
    if (cart == null) {
        cart = new HashMap<>();
    }

    // 2. Guardem la selecció: la clau és l'ID de la sessió (screening), 
    // i el valor és la llista d'IDs de seients seleccionats.
    cart.put(screeningId, seientsSeleccionats);

    // 3. Tornem a guardar el mapa actualitzat a la sessió
    session.setAttribute("cart", cart);

    // OPCIONAL: Per depurar i veure que tot va bé per consola
    System.out.println("Cart actualitzat: " + cart);

    // 4. Per mostrar el resum, recuperem les dades com feies abans
    Screening screening = screeningRepository.findById(screeningId).orElse(null);
    List<Seat> seientsObjectes = seatRepository.findAllById(seientsSeleccionats);

    model.addAttribute("screening", screening);
    model.addAttribute("seients", seientsObjectes);

    return "client/resum-compra";
}

@GetMapping("/client/home")
public String home() {
    
    return "client/home";
}

// // el que hi ha a la pissarra
//  @GetMapping("/screenings/seats/{id}")
//  public String selectSeats(@PathVariable Long id, Model model, HttpSession session){
//      Optional<Screening>screening=screeningRepository.findById(id);
//      if(screening.isEmpty()){
//          return "redirect:/client/movies";

//      }

//      Map<Long,List<Long>> cart=(Map<Long,List<Long>>)session.getAttribute("cart");
//      if(cart==null){
//         cart=new HashMap<>();
//      }

//      SeatsListDTO seatsListDTO= new SeatsListDTO();
//      seatsListDTO.setSeats(cart.get(id));
// model.addAttribute("selectedSeats", seatsListDTO);
//  model.addAttribute("screening",screening.get());
// return "client/movies/sets";

//  }


// // el que hi ha a la pissarra
// @PostMapping("/screenings/seats/confirm/{id}")
// public String confirSeats(@PathVariable Long id, @ModelAttribute SeatsListDTO selectedSeats, Model model, HttpSession session){
//     // obtenir mapa de la sessio o crear-lo

//     Map<Long, List<Long>> cart=(Map<Long, List<Long>>) session.getAttribute("cart");

//     if(cart==null){
//         cart=new HashMap<>();
//     }

//     cart.put(id, selectedSeats.getSeats());
//     session.setAttribute("cart",cart);
//     System.out.println("cart actualitzat: "+cart);
//     return "redirect:/client/movies/screenings/seats/"+id;
// }



@PostMapping("/client/reserva/afegir")
public String afegirAlCarret(@RequestParam Long screeningId, @RequestParam(required=false)List<Long>seientsSeleccionats, HttpSession session){
    Map<Long, List<Long>> cart = (Map<Long, List<Long>>) session.getAttribute("cart");
    if (cart == null) cart = new HashMap<>();

    if (seientsSeleccionats == null) seientsSeleccionats = new ArrayList<>();
    
    cart.put(screeningId, seientsSeleccionats);
    session.setAttribute("cart", cart);

    // IMPORTANT: Redirigim al GET per evitar l'error de "null"
    return "redirect:/client/carret"; 
}

@GetMapping("/client/carret")
public String veureCarret(HttpSession session, Model model) {
    Map<Long, List<Long>> cart = (Map<Long, List<Long>>) session.getAttribute("cart");
    List<Map<String, Object>> detallsCarret = new ArrayList<>();
    double totalGeneral = 0;

    if (cart != null) {
        for (Map.Entry<Long, List<Long>> entry : cart.entrySet()) {
            Screening screening = screeningRepository.findById(entry.getKey()).orElse(null);
            List<Seat> seients = seatRepository.findAllById(entry.getValue());

            if (screening != null && !seients.isEmpty()) {
                Map<String, Object> item = new HashMap<>();
                item.put("pelicula", screening.getMovie().getTitol());
                item.put("sessio", screening.getScreeningDateTime());
                item.put("seients", seients);
                double subtotal = seients.size() * screening.getPrice();
                item.put("subtotal", subtotal);
                detallsCarret.add(item);
                totalGeneral += subtotal;
            }
        }
    }

    // Aquí és on creem la variable "items" que Thymeleaf busca!
    model.addAttribute("items", detallsCarret);
    model.addAttribute("total", totalGeneral);
    
    return "client/carret";
}



}
   
        
    
