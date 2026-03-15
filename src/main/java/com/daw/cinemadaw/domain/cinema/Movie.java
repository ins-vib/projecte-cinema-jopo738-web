package com.daw.cinemadaw.domain.cinema;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Movie {


    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id; 
    
    @NotBlank(message="El títol és obligatori")
    @Size(min=5,max=150,message="El titol ha de tenir entre 2 i 100 caràcters")
     @Column(nullable=false, length=200)
    private String títol;  

    //@NotBlank(message="La durada és obligatoria")
   // @Size(min=5,max=150,message="La durada ha de tenir entre 2 i 100 caràcters")
     @Column (name="duration_minutes",nullable=false)
    private Integer durada; 
    
    @NotBlank(message="El gènere és obligatori")
    @Size(min=5,max=150,message="El gènere ha de tenir entre 2 i 100 caràcters")
     @Column (length=50)
    private String genere; 
    
    @NotBlank(message="La descripció és obligatoria")
    @Size(min=5,max=150,message="La descripció ha de tenir entre 2 i 100 caràcters")
     @Column (columnDefinition="TEXT")
    private String descripcio;  

    //@NotBlank(message="La data és obligatoria")
    //@Size(min=5,max=150,message="La data ha de tenir entre 2 i 100 caràcters")
     @Column (name="release_date")
     @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate data_estrena;  

    public Movie() {
    }

    public Movie(LocalDate data_estrena, String descripcio, int durada, String genere, String títol) {
        this.data_estrena = data_estrena;
        this.descripcio = descripcio;
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

    public Integer getDurada() {
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

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
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
        sb.append(", descripcio=").append(descripcio);
        sb.append(", data_estrena=").append(data_estrena);
        sb.append('}');
        return sb.toString();
    }








    
}
