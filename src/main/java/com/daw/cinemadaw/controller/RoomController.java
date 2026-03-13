package com.daw.cinemadaw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.daw.cinemadaw.domain.cinema.Cinema;
import com.daw.cinemadaw.domain.cinema.Room;
import com.daw.cinemadaw.repository.CinemaRepository;
import com.daw.cinemadaw.repository.RoomRepository;

import jakarta.validation.Valid;

@Controller
//@RequestMapping("/room")//prefix
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    public RoomController(CinemaRepository cinemaRepository, RoomRepository roomRepository) {
        this.cinemaRepository = cinemaRepository;
        this.roomRepository = roomRepository;
    }



// crear
 @GetMapping("/room/create/{cinemaId}")
    public String create_room(@PathVariable Long cinemaId,Model model){

        Room room = new Room();    // Tots els valors del formulari sorten amb blanc perque hem creat un nou cinema

        model.addAttribute("room",room);
        model.addAttribute("cinemaId",cinemaId);
            
        return "room/create-room";
    }

    @PostMapping("/room/create/{cinemaId}")
    public String guardarroom(@PathVariable Long cinemaId,@Valid @ModelAttribute("room") Room room, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("cinemaId",cinemaId);
            return "room/create-room";
        }
        Optional<Cinema> cinemaOpt=cinemaRepository.findById(cinemaId);
        if(cinemaOpt.isEmpty()){
            return"redirect:/cinema/";
        }

        Cinema cinema=(Cinema)cinemaOpt.get();
        room.setCinema(cinema);
        roomRepository.save(room);
        
        return "redirect:/cinema/" + cinemaId;
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
    public String actualitzarsala(@ModelAttribute Room room){
        Optional<Room>existingRoom=roomRepository.findById(room.getId());
        if(existingRoom.isEmpty()){
            return "redirect:/cinemes";
        }
        Room oldRoom = existingRoom.get();
        oldRoom.setName(room.getName());
        oldRoom.setCapacity(room.getCapacity());
        roomRepository.save(oldRoom);
        return "redirect:/cinema/"+oldRoom.getCinema().getId();
    
    }

    
}
