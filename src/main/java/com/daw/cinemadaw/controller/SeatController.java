package com.daw.cinemadaw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.daw.cinemadaw.domain.cinema.Room;
import com.daw.cinemadaw.domain.cinema.Seat;
import com.daw.cinemadaw.domain.cinema.SeatType;
import com.daw.cinemadaw.repository.RoomRepository;
import com.daw.cinemadaw.repository.SeatRepository;

import jakarta.validation.Valid;

@Controller
//@RequestMapping("/seats")
public class SeatController {
    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private RoomRepository roomRepository;

    public SeatController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // generar seients

    @GetMapping("/room/{id}/generate-seats")
    public String generateSeats(@PathVariable Long id){
        Optional<Room>roomOpt=roomRepository.findById(id);
        if(roomOpt.isPresent()){
            Room room=roomOpt.get();

            if(room.getSeats().isEmpty()){
                int capacity=room.getCapacity();
                int seatsperfila= 10;

                for(int i= 0; i<capacity; i++){
                    Seat seat=new Seat();
                    int row=(i/seatsperfila)+1;
                    int col= (i%seatsperfila)+1;
                    int numerocadira=i+1;

                    seat.setSeatRow(row);
                    seat.setNumber(numerocadira);
                    seat.setActive(true);
                    seat.setSeatType(SeatType.STANDARD);
                    seat.setPosX(col*50);
                    seat.setPosY(row*50);
                    seat.setRoom(room);
                    room.getSeats().add(seat);

                }
                roomRepository.save(room);
            }
       }
        return "redirect:/room/"+id;
    }


    @GetMapping("/seats/room/{id}")
    public String showSeats(@PathVariable Long id, Model model){
        Optional<Room>optional=roomRepository.findById(id);

        if(optional.isPresent()){
            Room room=optional.get();
            List<Seat>seats=room.getSeats();
            model.addAttribute("room", room);
            model.addAttribute("seats",seats);
            return "seat";
        }
        return "redirect:/cinemes";
    }


    @GetMapping("/seat/update/{id}")
        public String mostrarFormulariEditar(@PathVariable Long id, Model model){

            Optional<Seat> optional = seatRepository.findById(id);
            if(optional.isPresent()){
                model.addAttribute("seat",optional.get());
                model.addAttribute("rooms",roomRepository.findAll());

                 model.addAttribute("types",SeatType.values());
                 return "editar-seat";
            }

           
            
            return "redirect:/cinemes";

            
           
        }


        
        @PostMapping("/seats/edit")
        public String editCinema(@Valid @ModelAttribute("seat") Seat seat){
         
            seatRepository.save(seat);  // serveix per desar un nou i desar un actualitzat, crea un nou si no posem identificador
            return "redirect:/cinemes";
        }



        @GetMapping("/seats/delete/{id}")
        public String eliminarSeients(@PathVariable Long id){
            Optional<Seat>seatOpt=seatRepository.findById(id);
            if(seatOpt.isPresent()){
                Seat seat=seatOpt.get();
                Long roomId =seat.getRoom().getId();
                seatRepository.delete(seat);
                return "redirect:/seats/room/"+roomId;
            }
            return "redirect:/cinemes";
        }

        @GetMapping("/seats/room/{roomId}/new")
        public String crearNovaCadira(@PathVariable Long roomId, Model model){
            Optional<Room>roomOpt=roomRepository.findById(roomId);
            if(roomOpt.isPresent()){
                
                Seat seat=new Seat();
                model.addAttribute("seat",seat);
                model.addAttribute("roomId",roomId);
                model.addAttribute("types",SeatType.values());
                return "crear-seat";

            }
            return "redirect:/cinemes";
        }

        @PostMapping("/seats/room/{roomId}/save")
        public String guardarNouSeient(@PathVariable Long roomId, @ModelAttribute("seat")Seat seat){
            Optional <Room> roomOpt=roomRepository.findById(roomId);
            if(roomOpt.isPresent()){
                seat.setRoom(roomOpt.get());
                seat.setActive(true);
                seatRepository.save(seat);
                return "redirect:/seats/room/"+roomId;
            }
            return "redirect:/cinemes";
        }




    
}
