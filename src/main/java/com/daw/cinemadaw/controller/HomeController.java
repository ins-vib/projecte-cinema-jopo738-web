package com.daw.cinemadaw.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping("/")
    public String home(){
        // Retorna el nom de la vista que s'ha de mostrar a l'usuari.
        // En aquest cas, Spring buscarà un fitxer anomenat "home.html"
        return "home";
    }
}
