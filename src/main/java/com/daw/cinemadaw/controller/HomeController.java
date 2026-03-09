package com.daw.cinemadaw.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.daw.cinemadaw.domain.New;
import com.daw.cinemadaw.service.NewsService;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model){

        NewsService newsService = new NewsService();
        ArrayList<New> llista= new ArrayList<>();

        try {
            newsService.getNews();
        } catch (FileNotFoundException e) {
            System.err.println("No he pogut obrir el fitxer");
           
        }
        // Retorna el nom de la vista que s'ha de mostrar a l'usuari.
        // En aquest cas, Spring buscarà un fitxer anomenat "home.html"
        model.addAttribute("llista",llista);
        return "home";
    }
}
