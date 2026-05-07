package com.daw.cinemadaw.domain.cinema;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Movie {


    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id; 
    
    @NotBlank(message="El títol és obligatori")
    @Size(min=5,max=150,message="El titol ha de tenir entre 5 i 150 caràcters")
     @Column(nullable=false, length=200)
    private String titol;  

    //@NotBlank(message="La durada és obligatoria")
   // @Size(min=5,max=150,message="La durada ha de tenir entre 2 i 100 caràcters")
     @Column (name="duration_minutes",nullable=false)
    private Integer durada; 
    
    // @NotBlank(message="El gènere és obligatori")
    // @Size(min=5,max=150,message="El gènere ha de tenir entre 5 i 150 caràcters")
    //  @Column (length=50)
    // private String genere; 
    
    @NotBlank(message="La descripció és obligatoria")
    @Size(min=5,max=150,message="La descripció ha de tenir entre 5 i 150 caràcters")
     @Column (columnDefinition="TEXT")
    private String descripcio;  

    //@NotBlank(message="La data és obligatoria")
    //@Size(min=5,max=150,message="La data ha de tenir entre 2 i 100 caràcters")
     @Column (name="release_date")
     @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate data_estrena;  

    @ManyToMany
@JoinTable(
  name = "movie_genere", // Taula intermèdia que es crearà sola
  joinColumns = @JoinColumn(name = "movie_id"), 
  inverseJoinColumns = @JoinColumn(name = "genere_id"))
private List<Genere> generes = new ArrayList<>();

    public Movie() {
    }

    // public Movie(LocalDate data_estrena, String descripcio, int durada, String genere, String titol) {
    //     this.data_estrena = data_estrena;
    //     this.descripcio = descripcio;
    //     this.durada = durada;
    //     //this.genere = genere;
    //     this.titol = titol;
    // }
    



    // public Movie(Long id,
    //         @NotBlank(message = "El títol és obligatori") @Size(min = 5, max = 150, message = "El titol ha de tenir entre 5 i 150 caràcters") String titol,
    //         Integer durada
    //         ) {
    //     this.id = id;
    //     this.titol = titol;
    //     this.durada = durada;
    //     this.descripcio = descripcio;
    //     this.data_estrena = data_estrena;
    //     this.generes = generes;
    // }

    public Movie(Long id, String titol, Integer durada, String descripcio, LocalDate data_estrena, List<Genere> generes) {
    this.id = id;
    this.titol = titol;
    this.durada = durada;
    this.descripcio = descripcio;
    this.data_estrena = data_estrena;
    this.generes = generes != null ? generes : new ArrayList<>();
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public Integer getDurada() {
        return durada;
    }

    public void setDurada(int durada) {
        this.durada = durada;
    }

    // public String getGenere() {
    //     return genere;
    // }

    // public void setGenere(String genere) {
    //     this.genere = genere;
    // }

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
        return "Movie [id=" + id + ", titol=" + titol + ", durada=" + durada + ", descripcio=" + descripcio
                + ", data_estrena=" + data_estrena + ", generes=" + generes + ", getClass()=" + getClass()
                + ", getId()=" + getId() + ", getTitol()=" + getTitol() + ", getDurada()=" + getDurada()
                + ", hashCode()=" + hashCode() + ", getDescripcio()=" + getDescripcio() + ", getData_estrena()="
                + getData_estrena() + ", getGeneres()=" + getGeneres() + ", toString()=" + super.toString() + "]";
    }

    

    public List<Genere> getGeneres() {
        return generes;
    }

    public void setGeneres(List<Genere> generes) {
        this.generes = generes;
    }








    
}
