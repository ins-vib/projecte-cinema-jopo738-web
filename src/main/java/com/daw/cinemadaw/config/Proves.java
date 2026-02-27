package com.daw.cinemadaw.config;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.daw.cinemadaw.domain.cinema.Cinema;
import com.daw.cinemadaw.domain.cinema.Room;
import com.daw.cinemadaw.domain.cinema.Seat;
import com.daw.cinemadaw.domain.cinema.SeatType;
import com.daw.cinemadaw.repository.CinemaRepository;
import com.daw.cinemadaw.repository.RoomRepository;
import com.daw.cinemadaw.repository.SeatRepository;

import jakarta.transaction.Transactional;

@Component
public class Proves implements CommandLineRunner {   // es per provar que tot està funcionant 

    private CinemaRepository cinemaRepository; // la creem per poder treballar amb ella
    private RoomRepository roomRepository;
    private SeatRepository seatRepository;
    

    public Proves(CinemaRepository cinemaRepository,RoomRepository roomRepository, SeatRepository seatRepository) {
        this.cinemaRepository = cinemaRepository;
        this.roomRepository= roomRepository;
        this.seatRepository= seatRepository;
    }


    @Transactional
    @Override  // Indica que aquest mètode està sobrescrivint un mètode de la interfície pare (probablement CommandLineRunner). Serveix per assegurar-se que la signatura és correcta.
    public void run (String... args) throws Exception{  // Defineix el mètode que conté la lògica a executar. El paràmetre String... args permet rebre arguments des de la línia de comandes si fos necessari.

        Cinema cinema1 = new Cinema("Ocine", "Gavarres, 46", "Tarragona", "431220");
        cinemaRepository.save(cinema1);

        List<Cinema> llista = cinemaRepository.findAll(); // Aquí demanes al repositori que recuperi tots els registres de la taula de cinemes. El resultat es guarda en una llista d'objectes tipus Cinema.
        for(Cinema cinema: llista){  // És un bucle (for-each) que recorre la llista que acabes d'obtenir de la base de dades, element per element.
            System.out.println(cinema); // Imprimeix per la consola cada objecte cinema. Perquè es vegi bé (i no surti una adreça de memòria), la classe Cinema hauria de tenir definit el mètode toString().
        }
        
        // si nomes vull agafar un registre

    // 1. Busquem a la base de dades el cinema amb ID 4.
// Retorna un 'Optional', que és com una "caixa" que pot estar plena o buida.
// La 'L' de '1L' indica que el número és de tipus Long.
    Optional<Cinema>optionalCinema= cinemaRepository.findById(4L);

    // 2. Utilitzem una condició per verificar si el cinema realment existeix a la BD.
// 'isPresent()' retorna 'true' si s'ha trobat el registre i 'false' si no hi era.
    if(optionalCinema.isPresent()){

        // 3. Si la caixa no està buida, extraiem l'objecte real 'Cinema' amb el mètode .get().
    // Ara ja podem accedir a les seves propietats (nom, adreça, etc.).
        Cinema cinema = optionalCinema.get();
        // Imprimeix l'objecte 'cinema' per la consola del sistema.
        System.out.println(cinema);
        // 1. Modifiquem l'atribut 'city' de l'objecte que tenim a la memòria de Java.
// En aquest moment, el canvi NOMÉS existeix a la RAM, encara no a la base de dades.
        cinema.setCity("Reus");
        // 2. Tornem a cridar al mètode .save(). 
// Com que l'objecte 'cinema' ja té un ID que existeix a la BD (l'ID 4 que hem buscat abans),
// Spring Data JPA entén que no ha de crear un registre nou, sinó fer un UPDATE.
        cinemaRepository.save(cinema);
    }else{
        // 2. Imprimeix un missatge d'error o avís a la consola.
       // Això informa a l'usuari/programador que l'ID buscat (p.ex. el 1) no existeix a la BD.
        System.out.println("No trobat");
    }


    // 1. Cridem al mètode personalitzat del repositori per buscar cinemes de "Tarragona".
    // El resultat es guarda en una List (llista) perquè pot haver-hi més d'un cinema.
    List<Cinema> llista2 = cinemaRepository.findByCity("Tarragona");

    // 2. Iniciem un bucle 'for-each' per recórrer la llista obtinguda.
    // 'Cinema cinema' representa l'objecte actual en cada volta del bucle.
    for (Cinema cinema: llista2){

        // 3. Imprimim per consola les dades de cada cinema trobat a Tarragona.
       // Recordar que això cridarà al mètode .toString() de la teva classe Cinema.
        System.out.println(cinema);
    }

/**
 * ELIMINACIÓ D'UN REGISTRE ESPECÍFIC
 */

// 1. llista2.get(0): Obtenim el primer objecte Cinema de la llista (posició 0).
// 2. cinemaRepository.delete(...): Passem aquest objecte al mètode delete.
// Spring Data JPA buscarà el seu ID i executarà un "DELETE FROM cinema WHERE id = ?"
    cinemaRepository.delete(llista2.get(0)); 


/**
 * RE-LLISTAT GENERAL PER VERIFICAR CANVIS
 */

// 1. Tornem a demanar al repositori que ens doni TOTS els cinemes actuals.
// Si hem esborrat o editat algun, aquí ja veurem l'estat actual de la BD.
    llista=cinemaRepository.findAll();

    // 2. Iniciem el bucle per recórrer la llista actualitzada.
    for(Cinema cinema:llista){

        // 3. Imprimim cada cinema per veure els canvis reflectits a la consola.
    // Per exemple, aquí hauries de veure el cinema amb la ciutat "Reus" 
    // o deixar de veure el cinema que has eliminat.
        System.out.println(cinema);
    }

    Cinema cinema11= new Cinema("ocine", "Gavarres,45", "tarragona", "43206");
    cinemaRepository.save(cinema11);

    Room room1= new Room(50, "Sala 1");
    room1.setCinema(cinema11);
    roomRepository.save(room1);

    Room room2= new Room(60, "Sala 2");
    room1.setCinema(cinema11);
    roomRepository.save(room2);

    Room room3= new Room(70, "Sala 3");
    room1.setCinema(cinema11);
    roomRepository.save(room2);


    Cinema cinema3D= new Cinema("Cine 3d", "Gavarres,46", "Tarragona", "43567");
    Room room3D1= new Room(500, "sala 3d 1");
    room3D1.setCinema(cinema3D);
    cinema3D.getRooms().add(room3D1);
    cinemaRepository.save(cinema3D);

    

    for(Room room: roomRepository.findAll()){
        int seatCount= 0;
        for (int i = 0; i < 10; i++) {
            for(int j= 0; j<10;j++){
                char nouChar= (char)('A'+i);
                if(seatCount<50){
                    Seat seat=new Seat(true,i,i,j,room,j,SeatType.STANDARD);
                    seat.setRoom(room);
                    seatRepository.save(seat);
                }else if(seatCount<85){
                    Seat seat = new Seat(true, i, i, j, room,j, SeatType.STANDARD);
                    seat.setRoom(room);
                    seatRepository.save(seat);
                }else{
                    Seat seat =new Seat(true, i, i, j, room,j, SeatType.STANDARD);
                    seat.setRoom(room);
                    seatRepository.save(seat);

                }
                seatCount++;
            }
        }
roomRepository.save(room);
    }

    


    Optional<Cinema> optionalC=cinemaRepository.findById(1L);
    if(optionalC.isPresent()){
        Cinema c= optionalC.get();
        System.out.println(c);

        List<Room> sales= c.getRooms();
        for(Room room: sales){
            System.err.println(room);
            List<Seat> seients = room.getSeats();
            for(Seat s: seients){
                System.err.println(s);
            }
        }

    }




    

    
    }

    
}
