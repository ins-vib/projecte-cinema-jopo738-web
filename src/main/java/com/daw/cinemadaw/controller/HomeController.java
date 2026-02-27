package com.daw.cinemadaw.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.daw.cinemadaw.domain.cinema.Cinema;
import com.daw.cinemadaw.repository.CinemaRepository;

// Indica que aquesta classe és un controlador web. 
// Spring la detectarà automàticament i sabrà que ha de gestionar peticions HTTP.
@Controller  
public class HomeController {

    // 1. Atribut privat per guardar la referència al repositori
    private CinemaRepository cinemaRepository;

    // 2. Constructor de la classe (ATENCIÓ: No ha de portar "void")
// Spring utilitza aquest constructor per injectar el repositori automàticament
    public HomeController(CinemaRepository cinemaRepository){
        this.cinemaRepository=cinemaRepository;

    }
    
    // Defineix que quan l'usuari accedeixi a l'URL arrel (/home) mitjançant un mètode GET (gràcies al GetMapping és GET)
    // (per exemple, escrivint la direcció al navegador), s'executarà aquest mètode. S'ha de posar /home al URL
    @GetMapping("/")
    public String home(){
        // Retorna el nom de la vista que s'ha de mostrar a l'usuari.
        // En aquest cas, Spring buscarà un fitxer anomenat "home.html"
        return "home";
    }

    @GetMapping("/cinemes")
    public String cinemes(Model model){

        // 1. Recuperem la llista de tots els cinemes de la base de dades
    // fent servir el repositori que hem injectat abans.
        List<Cinema> cinemes = cinemaRepository.findAll();
        // 2. Afegim la llista al model. 
    // "llista" és el nom (clau) que farem servir al fitxer HTML (Thymeleaf).
    // "cinemes" és la variable Java que conté les dades reals.
        model.addAttribute("llista",cinemes);  // dades que li passem al model vista


        // Retorna el nom de la vista que s'ha de mostrar a l'usuari.
        // En aquest cas, Spring buscarà un fitxer anomenat "cinemes.html"
        return "cinemes";
    }

}
