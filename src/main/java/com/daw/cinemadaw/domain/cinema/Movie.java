package com.daw.cinemadaw.domain.cinema;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Movie {


    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id; 
    
     @Column 
    private String títol;  
     @Column 
    private int durada;  
     @Column 
    private String genere;  
     @Column 
    private String descrpcio;  
     @Column 
    private LocalDate data_estrena;  

    public Movie() {
    }

    public Movie(LocalDate data_estrena, String descrpcio, int durada, String genere, String títol) {
        this.data_estrena = data_estrena;
        this.descrpcio = descrpcio;
        this.durada = durada;
        this.genere = genere;
        this.títol = títol;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTítol() {
        return títol;
    }

    public void setTítol(String títol) {
        this.títol = títol;
    }

    public int getDurada() {
        return durada;
    }

    public void setDurada(int durada) {
        this.durada = durada;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getDescrpcio() {
        return descrpcio;
    }

    public void setDescrpcio(String descrpcio) {
        this.descrpcio = descrpcio;
    }

    public LocalDate getData_estrena() {
        return data_estrena;
    }

    public void setData_estrena(LocalDate data_estrena) {
        this.data_estrena = data_estrena;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Movie{");
        sb.append("id=").append(id);
        sb.append(", t\u00edtol=").append(títol);
        sb.append(", durada=").append(durada);
        sb.append(", genere=").append(genere);
        sb.append(", descrpcio=").append(descrpcio);
        sb.append(", data_estrena=").append(data_estrena);
        sb.append('}');
        return sb.toString();
    }








    
}
