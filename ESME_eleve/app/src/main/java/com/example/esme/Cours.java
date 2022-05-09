package com.example.esme;

import java.io.Serializable;

public class Cours implements Serializable {
    public String heureDebut,heureFin,date,matiere,intitule,professeur,salle,importance;
    public boolean estPerso;
    public Cours() {
        this.matiere="matière";
        this.date="01/01/1970";
        this.intitule="intitulé";
        this.heureDebut="HeureDébut";
        this.heureFin="HeureFin";
        this.professeur="Prof";
        this.salle="Salle";
        this.importance="Importance";
        this.estPerso=false;
    }
}
