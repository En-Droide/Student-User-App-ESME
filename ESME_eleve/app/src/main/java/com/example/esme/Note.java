package com.example.esme;

import java.util.ArrayList;

public class Note {
    public String date,matiere;
    public Double note,coefficient;

    public Note(String date,String matiere,Double note,Double coefficient) {
        this.matiere=matiere;
        this.date=date;
        this.note=note;
        this.coefficient=coefficient;
    }
    public Note() {
        this.matiere="Placeholder matière";
        this.date="01-01-1970";
        this.note=10.00;
        this.coefficient=1.00;
    }
}
