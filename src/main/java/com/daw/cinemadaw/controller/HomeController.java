package com.daw.cinemadaw.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.daw.cinemadaw.domain.New;
import com.daw.cinemadaw.domain.cinema.Movie;
import com.daw.cinemadaw.domain.user.Role;
import com.daw.cinemadaw.domain.user.User;
import com.daw.cinemadaw.repository.MovieRepository;
import com.daw.cinemadaw.repository.UserRepository;
import com.daw.cinemadaw.service.NewsService;



@Controller
public class HomeController {


@Autowired
private UserRepository userRepository;

@Autowired
private BCryptPasswordEncoder passwordEncoder;

@Autowired
private MovieRepository movieRepository;

 



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

    
}
