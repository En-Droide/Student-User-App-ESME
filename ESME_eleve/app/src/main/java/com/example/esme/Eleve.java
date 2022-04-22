package com.example.esme;

public class Eleve {
    public String username,email,nom,prenom;

    public Eleve() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }
    public Eleve(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
}
