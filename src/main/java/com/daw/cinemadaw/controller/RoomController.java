package com.daw.cinemadaw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.daw.cinemadaw.domain.cinema.Cinema;
import com.daw.cinemadaw.domain.cinema.Room;
import com.daw.cinemadaw.repository.CinemaRepository;
import com.daw.cinemadaw.repository.RoomRepository;

@Controller
//@RequestMapping("/room")//prefix
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CinemaRepository cinemaRepository;


 @GetMapping("/room/create")
    public String create_room(@RequestParam("cinemaId") Long cinemaId,Model model){

        Room room = new Room();    // Tots els valors del formulari sorten amb blanc perque hem creat un nou cinema

       Cinema cinema= cinemaRepository.findById(cinemaId).orElseThrow();
       room.setCinema(cinema);
       
       
        
            
            model.addAttribute("sala", room);
            
        return "room/create-room";
    }

    @PostMapping("/room/create")
    public String guardarroom(@ModelAttribute("sala") Room room){
        roomRepository.save(room);
        return "redirect:/cinema/" + room.getCinema().getId();
    }

    //detall 
    @GetMapping("/room/{id}")
    public String show(@PathVariable Long id, Model model){
        Optional<Room>optional=roomRepository.findById(id);
        if(optional.isEmpty()){
            return "redirect:/cinema/";
        }
        Room room=optional.get();
        model.addAttribute("room",room);
        return "room/detall-room";
    }

    // delete
    @GetMapping("/room/{id}/delete")
    public String delete(@PathVariable Long id){
        Optional<Room>optional =roomRepository.findById(id);
        Long cinemaId=null;
        Room room =null;

        if(optional.isPresent()){
            room= optional.get();
            cinemaId=room.getCinema().getId();
            roomRepository.delete(room);

        }
        return "redirect:/cinema/"+cinemaId;
    }

    //editar

    @GetMapping("/room/{id}/edit")
    public String editarformulari(@PathVariable Long id, Model model){
        Optional<Room> optional= roomRepository.findById(id);
        if(optional.isPresent()){
            model.addAttribute("sala",optional.get());
            return "room/editar-room";
        }
        return "redirect:/cinemes";
    }

    @PostMapping("/room/edit")
    public String actualitzarsala(@ModelAttribute("sala") Room room){

        Room salaBD = roomRepository.findById(room.getId()).orElseThrow();
    
    
    salaBD.setName(room.getName());
    salaBD.setCapacity(room.getCapacity());
    
    
    roomRepository.save(salaBD);

    Long idCinema= salaBD.getCinema().getId();

    if(idCinema==null){
        return "redirect:/cinemes";
    }
       return "redirect:/cinema/" + idCinema;
    }

    
}
