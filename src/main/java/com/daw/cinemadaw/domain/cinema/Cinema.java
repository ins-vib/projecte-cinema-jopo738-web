package com.daw.cinemadaw.domain.cinema;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity  // li diem que aquesta classe és una entitat i s'ha de guardar a la base de dades
public class Cinema {   

    @Id //Indica que aquest atribut és la clau primària (PK)
    @GeneratedValue(strategy=GenerationType.IDENTITY) // Genera automàticament l'ID al inserir un nou registre
    private Long id; // el Long permet guardar registres molts llargs
    
    @NotBlank(message="La ciutat és obligatoria")
    @Size(min=2,max=100,message="El cinema ha de tenir entre 2 i 100 caràcters")
    @Column // si només posem column vol dir que aquest atribut (el de sota) és una columna, al costat del column podem posar el nom que volem per aquella columna
    private String name;  // nom del cinema

    @NotBlank(message="La ciutat és obligatoria")
    @Size(min=5,max=150,message="L'adreça ha de tenir entre 2 i 100 caràcters")
    @Column
    private String address;  // adreça

    @NotBlank(message="La ciutat és obligatoria")
    @Size(min=2,max=80,message="La ciutat ha de tenir entre 2 i 100 caràcters")
    @Column
    private String city; //ciutat

    @NotBlank(message="La ciutat és obligatoria")  
    @Pattern(regexp="\\d{5}",message="El codi postal del cinema ha de ser un número de 5 digits") 
    @Column
    private String postalCode;  // codi postal 

    @OneToMany(mappedBy="cinema",cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Room>rooms=new ArrayList<>();

    
    public Cinema() {   // aquest constructor és obligatori perque el framework el busca
    }

    public String getName() {   
        return name;
    }

    
    public Cinema(String name, String address, String city, String postalCode) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    

    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cinema{");
        sb.append("id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", address=").append(address);
        sb.append(", city=").append(city);
        sb.append(", postalCode=").append(postalCode);
        sb.append('}');
        return sb.toString();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }



    

    

}
