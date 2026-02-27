package com.daw.cinemadaw.domain.cinema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Seat {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int seatRow;

    @Column
    private int number;

    @Column
    private  int posX;

    @Column
    private int posY;


    @Column
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @Column
    private boolean active;

    @ManyToOne
    private Room room;

    public Seat() {
    }

    public Seat(boolean active, int number, int posX, int posY, Room room, int seatRow, SeatType seatType) {
        this.active = active;
        this.number = number;
        this.posX = posX;
        this.posY = posY;
        this.room = room;
        this.seatRow = seatRow;
        this.seatType = seatType;
    }

    public Long getId() {
        return id;
    }

    

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Seat{");
        sb.append("id=").append(id);
        sb.append(", seatRow=").append(seatRow);
        sb.append(", number=").append(number);
        sb.append(", posX=").append(posX);
        sb.append(", posY=").append(posY);
        sb.append(", seatType=").append(seatType);
        sb.append(", active=").append(active);
        sb.append(", room=").append(room);
        sb.append('}');
        return sb.toString();
    }







    



}
