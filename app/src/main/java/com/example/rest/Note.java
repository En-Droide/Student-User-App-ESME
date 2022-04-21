package com.example.rest;

import org.json.JSONObject;
public class Note {
    private final int id,id_eleve;
    private final String matiere;
    private final double note;

    public Note(JSONObject jObject) {
        this.id =jObject.optInt("id");
        this.id_eleve =jObject.optInt("id_eleve");
        this.matiere = jObject.optString("matiere");
        this.note = jObject.optDouble("note");
    }

    public int getId() {
        return id;
    }

    public int getId_eleve() {
        return id_eleve;
    }

    public String getMatiere() {
        return matiere;
    }

    public double getNote() {
        return note;
    }
}
