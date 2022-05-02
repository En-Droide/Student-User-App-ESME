package com.example.esme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Eleve {
    public String email,nom,prenom,classe;
    public ArrayList<Note> notes;
    public ArrayList<Cours> emploidutemps;

    public Eleve() {
        this.nom="Nom";
        this.prenom="Prenom";
        this.email="Email";
        this.classe="Classe";
        this.notes=new ArrayList<>();
        this.emploidutemps=new ArrayList<>();

    }
}