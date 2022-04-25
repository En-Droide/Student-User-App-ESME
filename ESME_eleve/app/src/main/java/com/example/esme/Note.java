package com.example.esme;

public class Note {
    public String date,matiere,intitule;
    public Double note,coefficient;

    public Note(String date,String matiere,String intitule,Double note,Double coefficient) {
        this.matiere=matiere;
        this.date=date;
        this.intitule=intitule;
        this.note=note;
        this.coefficient=coefficient;
    }
    public Note() {
        this.matiere="Placeholder matière";
        this.date="01-01-1970";
        this.note=10.00;
        this.coefficient=1.00;
        this.intitule="Placeholder intitulé";
    }
}
