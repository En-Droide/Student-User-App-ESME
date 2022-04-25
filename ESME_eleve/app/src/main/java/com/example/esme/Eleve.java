package com.example.esme;

import java.io.Serializable;
import java.util.ArrayList;

public class Eleve {
    public String email,nom,prenom;
    public ArrayList<Note> notes;

    public Eleve() {
        this.nom="placeholder nom";
        this.prenom="placeholder prenom";
        this.email="placeholder email";
        this.notes=new ArrayList<>();
    }
}