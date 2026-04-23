package com.daw.cinemadaw.controller;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.daw.cinemadaw.domain.New;
import com.daw.cinemadaw.domain.cinema.Movie;
import com.daw.cinemadaw.domain.cinema.Screening;
import com.daw.cinemadaw.domain.cinema.Seat;
import com.daw.cinemadaw.domain.ticket.Order;
import com.daw.cinemadaw.domain.ticket.Ticket;
import com.daw.cinemadaw.domain.user.Role;
import com.daw.cinemadaw.domain.user.User;
import com.daw.cinemadaw.repository.MovieRepository;
import com.daw.cinemadaw.repository.OrderRepository;
import com.daw.cinemadaw.repository.ScreeningRepository;
import com.daw.cinemadaw.repository.SeatRepository;
import com.daw.cinemadaw.repository.UserRepository;
import com.daw.cinemadaw.service.NewsService;

import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {


@Autowired
private UserRepository userRepository;

@Autowired
private BCryptPasswordEncoder passwordEncoder;

@Autowired
private MovieRepository movieRepository;

@Autowired
private ScreeningRepository screeningRepository;

@Autowired
private SeatRepository seatRepository;

@Autowired
private OrderRepository orderRepository;

 



    @GetMapping("/")
    public String home(Model model){

        NewsService newsService = new NewsService();
        ArrayList<New>llista= new ArrayList<>();

        try {
            newsService.getNews();
            llista = newsService.getNews();
        } catch (FileNotFoundException e) {
            System.err.println("No he pogut obrir el fitxer");
           
        }
        // Retorna el nom de la vista que s'ha de mostrar a l'usuari.
        // En aquest cas, Spring buscarà un fitxer anomenat "home.html"
        model.addAttribute("llista",llista);
        return "landing";
    }

     // Mostra la pàgina de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    

    // Pàgina d'admin
    @GetMapping("/admin")
    public String admin() {
        return "admin/home";
    }

    // Pàgina de client
    @GetMapping("/client")
    public String client(Model model) {
        List<Movie> pelis = movieRepository.findAll();
    
    
    model.addAttribute("llista", pelis);
       
        return "client/home";
    }

  @GetMapping("/registrar")
    public String mostrarRegistre(Model model) {
        model.addAttribute("user", new User());
        return "registrar";
    }

    @PostMapping("/registrar/save")
    public String guardarUsuari(@ModelAttribute User user) {

        String passwordEncriptada = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncriptada);
        user.setRole(Role.CLIENT);
        userRepository.save(user);
        return "redirect:/login";
    }

    @GetMapping("/cart/checkout")  // /cart/checkout
    public String checkout (Model model,HttpSession session, @AuthenticationPrincipal UserDetails userDetails){
        String username=userDetails.getUsername();
        //User user= userRepository.findByUsername(username).orElse(null);   // el que diu el chat
        Optional<User>user=userRepository.findByUsername(username);   
        System.out.println("Usuari autenticat: "+user);

        if(user.isEmpty()){
            return "redirect:/client/movies/";
        }

       
        Map<Long, List<Long>> cart= (Map<Long,List<Long>>) session.getAttribute("cart");

        if(cart==null || cart.isEmpty()){
            return "redirect:/client/home/";     // "redirect:/client/movies/";
        }

        Order order=new Order();
        order.setUser(user.get());
        order.setDataHora(LocalDateTime.now());
        order.setEstat("FINALITZADA");

        double total=0;

        for(Map.Entry<Long,List<Long>> entry : cart.entrySet()){
            Screening screening = screeningRepository.findById(entry.getKey()).orElse(null);
            List<Seat>seat=seatRepository.findAllById(entry.getValue());

            if(screening != null){
                for(Seat s: seat){
                    Ticket ticket= new Ticket();
                    ticket.setOrder(order);
                    ticket.setScreening(screening);
                    ticket.setSeat(s);
                    ticket.setPreu(screening.getPrice());


                    order.getTickets().add(ticket);
                    total=total+screening.getPrice();
                }
            }
        }

        order.setImportTotal(total);

        orderRepository.save(order);
        session.removeAttribute("cart");


        

        model.addAttribute("order", order); // Afegim l'ordre al model per veure les dades
        return "client/confirmacio-order";      // "client/cart/checkout"
    }


    @GetMapping("/client/llista-comandes")
    public String llistarComandes(Model model, @AuthenticationPrincipal UserDetails userDetails){
        String username=userDetails.getUsername();
        User user= userRepository.findByUsername(username).orElse(null);

        if(user != null){
            List<Order>comandes=orderRepository.findByUserId(user.getId());
            model.addAttribute("comandes",comandes);
        }

        return "client/llista-comandes";
    }

    
}
