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

import com.daw.cinemadaw.domain.cinema.Cinema;
import com.daw.cinemadaw.repository.CinemaRepository;

import jakarta.validation.Valid;

// Indica que aquesta classe és un controlador web. 
// Spring la detectarà automàticament i sabrà que ha de gestionar peticions HTTP.
@Controller  
public class CinemaController {

    // 1. Atribut privat per guardar la referència al repositori
    private CinemaRepository cinemaRepository;

    // 2. Constructor de la classe (ATENCIÓ: No ha de portar "void")
// Spring utilitza aquest constructor per injectar el repositori automàticament
    public CinemaController(CinemaRepository cinemaRepository){
        this.cinemaRepository=cinemaRepository;

    }
    
    // Defineix que quan l'usuari accedeixi a l'URL arrel (/home) mitjançant un mètode GET (gràcies al GetMapping és GET)
    // (per exemple, escrivint la direcció al navegador), s'executarà aquest mètode. S'ha de posar /home al URL
    

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

        //Detall Cinema

        // L'anotació @GetMapping defineix una ruta dinàmica. 
// El segment {id} actua com una variable que rebrà el codi del cinema des de l'URL.
        @GetMapping("/cinema/{id}")
        public String detall(@PathVariable Long id, Model model){

    
            Optional<Cinema> optional=cinemaRepository.findById(id);
            if(optional.isPresent()){
                Cinema cinema= optional.get();
                model.addAttribute("cinema", cinema);
                return "detall-cinema";
            }
           return "redirect:/";
        }


        //delete
        @GetMapping("/cinema/delete/{id}")
        public String delete(@PathVariable Long id){

            Optional<Cinema> optional=cinemaRepository.findById(id);
            if(optional.isPresent()){
                Cinema cinema= optional.get();
                cinemaRepository.deleteById(id);     
            } 

            return "redirect:/cinemes";
        }

       

        
        
        @GetMapping("/cinema/create")
        public String mostrarFormulariAlta(Model model){
            Cinema cinema = new Cinema();    // Tots els valors del formulari sorten amb blanc perque hem creat un nou cinema
            cinema.setCity("Tarragona");   // amb aixo posem el valor per fefecte de Tarragona a la ciutat al formulari de crear cinema
            model.addAttribute("cinema", cinema);
            return "create-cinema";
        }
        
        
        @PostMapping("/cinema/create")
        public String altaCinema(@Valid @ModelAttribute Cinema cinema, BindingResult result){

            if(result.hasErrors()){
                return"create-cinema";
            }
            cinemaRepository.save(cinema);
            return "redirect:/cinemes";
        }

        @GetMapping("/cinema/update/{id}")
        public String mostrarFormulariEditar(@PathVariable Long id, Model model){

            Optional<Cinema> optional = cinemaRepository.findById(id);
            if(optional.isPresent()){
                Cinema cinema = optional.get();
                model.addAttribute("cinema",cinema);
                 return "editar-cinema";
            }
            return "redirect:/cinemes";

            
           
        }


        
        @PostMapping("/cinema/edit")
        public String editCinema(@Valid @ModelAttribute("cinema") Cinema cinema, BindingResult result){
            if (result.hasErrors()) {
       
        return "editar-cinema"; 
    }
            cinemaRepository.save(cinema);  // serveix per desar un nou i desar un actualitzat, crea un nou si no posem identificador
            return "redirect:/cinemes";
        }
    }


