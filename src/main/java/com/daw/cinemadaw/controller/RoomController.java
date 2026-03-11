package com.daw.cinemadaw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daw.cinemadaw.domain.cinema.Room;
import com.daw.cinemadaw.repository.RoomRepository;

@Controller
@RequestMapping("/room")//prefix
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;


 @GetMapping("/create")
    public String create_room(Model model){

       Room room = new Room();    // Tots els valors del formulari sorten amb blanc perque hem creat un nou cinema
            
            model.addAttribute("sala", room);
            
        return "room/create-room";
    }

    @PostMapping("/create")
    public String guardarroom(@ModelAttribute("sala") Room room){
        roomRepository.save(room);
        return "redirect:/movies";
    }

    //detall 
    @GetMapping("/{id}")
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
    @GetMapping("{id}/delete")
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

    
}
