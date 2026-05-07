package com.daw.cinemadaw.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.daw.cinemadaw.domain.menjar.Menjar;
import com.daw.cinemadaw.domain.ticket.Ticket;
import com.daw.cinemadaw.repository.GenereRepository;
import com.daw.cinemadaw.repository.MenjarRepository;
import com.daw.cinemadaw.repository.MovieRepository;
import com.daw.cinemadaw.repository.OrderRepository;
import com.daw.cinemadaw.repository.ScreeningRepository;
import com.daw.cinemadaw.repository.SeatRepository;
import com.daw.cinemadaw.repository.TicketRepository;
import com.daw.cinemadaw.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
public class MovieController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
private UserRepository userRepository; // Necessari per buscar l'usuari per nom

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
private TicketRepository ticketRepository;

@Autowired
private OrderRepository orderRepository;

@Autowired
private MenjarRepository menjarRepository; // La variable comença en minúscula

@Autowired
    private MovieRepository movieRepository;

    @Autowired
    private GenereRepository genereRepository;

    //private MovieRepository movieRepository;

    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    //@GetMapping("/movies")
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
public String create_movies(Model model) {

    Movie movie = new Movie(); 
    model.addAttribute("pelicula", movie);

    // AQUESTA ÉS LA LÍNIA QUE ET FALTA:
    // Agafem els gèneres de la BD i els enviem a l'HTML amb el nom "totsElsGeneres"
    model.addAttribute("totsElsGeneres", genereRepository.findAll()); 
            
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
public String mostrarFormulariEditar(@PathVariable Long id, Model model) {
    // 1. Busquem la pel·lícula per ID
    Movie movie = movieRepository.findById(id).orElseThrow();
    model.addAttribute("pelicula", movie);
    
    // 2. CARREGUEM ELS GÈNERES (Això és el que et falta perquè surtin!)
    model.addAttribute("totsElsGeneres", genereRepository.findAll());
    
    return "movies/editar-pelicules";
}
        
        @PostMapping("/pelicula/edit")
        public String editCinema(@Valid @ModelAttribute("pelicula") Movie pelicula, BindingResult result,Model model){


            if(result.hasErrors()){
                return "movies/editar-pelicules";
            }
            movieRepository.save(pelicula);  // serveix per desar un nou i desar un actualitzat, crea un nou si no posem identificador
            return "redirect:/movies";
        }


       

        // //delete
        // @GetMapping("/pelicula/delete/{id}")
        // public String delete(@PathVariable Long id){

        //     Optional<Movie> optional=movieRepository.findById(id);
        //     if(optional.isPresent()){
        //         Movie pelicula= optional.get();
        //         movieRepository.deleteById(id);     
        //     } 

        //     return "redirect:/movies";
        // }
@GetMapping("/pelicula/delete/{id}")
@Transactional // IMPORTANT: Afegeix això perquè s'executin les dues operacions com una sola
public String delete(@PathVariable Long id){

    Optional<Movie> optional = movieRepository.findById(id);
    
    if(optional.isPresent()){
        // 1. Busquem i eliminem totes les projeccions associades a aquesta pel·lícula
        // Això evita l'error de "Referential integrity constraint violation"
        screeningRepository.deleteByMovieId(id); 

        // 2. Ara que ja no hi ha "fills" (projeccions), podem eliminar el "pare" (pel·lícula)
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

    // Screening screening = screeningOpt.get();

    // LÒGICA NOVA: Buscar seients ja ocupats en aquesta sessió (evita error 500)
    // Suposant que Screening té una llista de Tickets o que pots buscar-los al TicketRepository
    // Si no tens el TicketRepository injectat aquí, afegeix-lo a dalt amb @Autowired
    List<Long> occupiedSeatIds = ticketRepository.findByScreeningId(id) // Crea aquest mètode al repo
            .stream()
            .map(t -> t.getSeat().getId())
            .collect(Collectors.toList());

    if (occupiedSeatIds == null) occupiedSeatIds = new ArrayList<>();

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
    model.addAttribute("occupiedSeatIds", occupiedSeatIds); 
    //model.addAttribute("screening", screeningOpt.get());

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

    // 4. Per mostrar el resum, recuperem les dades 
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

    // Aquí és on creem la variable "items" que Thymeleaf busca
    model.addAttribute("items", detallsCarret);
    model.addAttribute("total", totalGeneral);

    
    
    return "client/carret";
}

@GetMapping("/client/complements")
public String mostrarComplements(Model model){
    List<Menjar>llistaMenjar=menjarRepository.findAll();
    model.addAttribute("productesMenjar",llistaMenjar);
    return "client/complements";
}


@PostMapping("/client/reserva/finalitzar")
public String finalitzarCompra(
        @RequestParam(required = false) Long foodId, 
        @RequestParam(defaultValue = "1") int quantitat, 
        HttpSession session, 
        java.security.Principal principal, 
        Model model) {

    // 1. Recuperem el carret i l'usuari
    Map<Long, List<Long>> cart = (Map<Long, List<Long>>) session.getAttribute("cart");
    com.daw.cinemadaw.domain.user.User usuariActual = userRepository.findByUsername(principal.getName()).orElse(null);

    if (cart == null || cart.isEmpty()) {
        return "redirect:/client/carret";
    }

    // 2. CREEM L'ORDRE AMB ELS TEUS CAMPS REALS
    com.daw.cinemadaw.domain.ticket.Order novaOrdre = new com.daw.cinemadaw.domain.ticket.Order();
    novaOrdre.setUser(usuariActual);
    novaOrdre.setDataHora(java.time.LocalDateTime.now()); // Per a la columna DATA I HORA
    novaOrdre.setEstat("PAGAT"); // Perquè no surti buit el quadrat de l'estat
    
    

    // Guardem l'ordre inicial (necessari per tenir ID per als tickets)
    orderRepository.save(novaOrdre); 

    double totalAcumulat = 0;

    // 3. BUCLE PER GUARDAR ELS TICKETS
    for (Map.Entry<Long, List<Long>> entry : cart.entrySet()) {
        Long screeningId = entry.getKey();
        List<Long> seientsIds = entry.getValue();

        Screening screening = screeningRepository.findById(screeningId).orElse(null);
        if (screening != null) {
            totalAcumulat += (screening.getPrice() * seientsIds.size());

            for (Long seatId : seientsIds) {
                Seat seat = seatRepository.findById(seatId).orElse(null);
                if (seat != null) {
                    Ticket t = new Ticket();
                    t.setScreening(screening);
                    t.setSeat(seat);
                    t.setUser(usuariActual);
                    t.setOrder(novaOrdre); 
                    ticketRepository.save(t);
                }
            }
        }
    }

    // 4. GESTIONEM EL MENJAR
    if (foodId != null) {
        Menjar menjarSeleccionat = menjarRepository.findById(foodId).orElse(null);
        if (menjarSeleccionat != null) {
            double preuMenjar = menjarSeleccionat.getPreu() * quantitat;
            totalAcumulat += preuMenjar;
        }
    }

    // 5. ACTUALITZEM L'IMPORT TOTAL 
    novaOrdre.setImportTotal(totalAcumulat); 
    orderRepository.save(novaOrdre); 

    // 6. FINALITZEM
    model.addAttribute("totalFinal", totalAcumulat);
    session.removeAttribute("cart");

    return "client/confirmacio-exit"; 
        }
@GetMapping("/movies")
public String listMovies(Model model) {
    // El nom de la variable ha de ser "llista" perquè així ho tens al th:each de l'HTML
    model.addAttribute("llista", movieRepository.findAll());
    return "movies/pelicules";
}

    // 2. FORMULARI PER A NOVA PEL·LÍCULA
@GetMapping("/new") // O la ruta que usis per obrir el formulari
public String showNewForm(Model model) {
    model.addAttribute("pelicula", new Movie());
    
    // AQUESTA LÍNIA ÉS LA QUE PORTA ELS GÈNERES DE L'H2 A L'HTML:
    model.addAttribute("totsElsGeneres", genereRepository.findAll());
    
    return "create-pelicules";
}

  @PostMapping("/movies/save")
public String saveMovie(@ModelAttribute("pelicula") Movie movie, BindingResult result, Model model) {
    if (result.hasErrors()) {
        // Si hi ha algun error de validació (com el títol buit), tornem a carregar els gèneres
        model.addAttribute("totsElsGeneres", genereRepository.findAll());
        movieRepository.save(movie);
        return "movies/create-pelicules";
    }
    
    movieRepository.save(movie);
    return "redirect:/movies";
}

    // 4. FORMULARI PER EDITAR
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID invàlid: " + id));
        
        model.addAttribute("movie", movie);
        
        // També necessitem els gèneres per poder-los marcar (checked) a l'edició
        model.addAttribute("totsElsGeneres", genereRepository.findAll());
        
        return "movies-form";
    }

    // 5. ELIMINAR
    @GetMapping("/delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id) {
        movieRepository.deleteById(id);
        return "redirect:/movies";
    }

    //@GetMapping("/pelicula/update/{id}") // O la ruta que tinguis per editar
public String editMovie(@PathVariable Long id, Model model) {
    Movie movie = movieRepository.findById(id).orElseThrow();
    model.addAttribute("pelicula", movie);
    
    // AIXÒ ÉS EL QUE ET FALTA: enviar tots els gèneres disponibles
    model.addAttribute("totsElsGeneres", genereRepository.findAll());
    
    return "movies/editar-pelicules";
}


}
