package com.daw.cinemadaw.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.daw.cinemadaw.domain.New;

public class NewsService {

   
      public ArrayList<New> getNews() throws FileNotFoundException{

        ArrayList<New>newlist= new ArrayList<>();
        // Llegir un fitxer de text línia a línia
       File f = new File("news.txt");
        if (f.exists()) {
                // llegir l'arxiu
                Scanner lectorFitxer = new Scanner(f);
                String linia;
                while(lectorFitxer.hasNextLine()) {
                    linia = lectorFitxer.nextLine();
                    String[] camps=linia.split(":");// aixo ho posem per separar el titular de la nnoticia
                    if(camps.length==2){
                    New n = new New(camps[0],camps[1]);
                    newlist.add(n);
                    }
                    System.out.println(linia);
                }

                
        }
        else {
                System.out.println("No existeix l'arxiu");
        } 
        return newlist;
      } 
   
   
}
