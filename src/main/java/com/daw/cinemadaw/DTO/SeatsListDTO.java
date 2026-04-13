package com.daw.cinemadaw.DTO;

import java.util.List;

public class SeatsListDTO {
    
     private List<Long>seats;

     public List<Long> getSeats(){
    return seats;
    }

    public void setSeats(List<Long>seats){
        this.seats=seats;
    }
}
