package com.example.esme;

public class Eleve {
    public String username;
    public String email;

    public Eleve() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Eleve(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
