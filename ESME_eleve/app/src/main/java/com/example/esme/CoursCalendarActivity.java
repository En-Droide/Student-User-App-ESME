package com.example.esme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class CoursCalendarActivity extends Activity {

    private Button return_cal;
    private TextView theDate;
    private RecyclerView recyclerViewCours;
    public static Eleve eleve;
    private ArrayList<Cours> coursdujour=new ArrayList<>();




    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cours_calendar);
        theDate = (TextView) findViewById(R.id.date_calendrier);
        return_cal = (Button) findViewById(R.id.return_cal);

        Intent incomingIntent = getIntent();
        String date = incomingIntent.getStringExtra("date");
        String dateformat = incomingIntent.getStringExtra("dateformat");
        theDate.setText(date);
        /*System.out.println("Calendrier du jour");*/
        eleve=CalendarActivity.eleve;
        eleve.emploidutemps.forEach(n -> {
            /*System.out.println("cours = "+n.matiere+" le "+n.date);*/
            if(n.date.equals(dateformat)){
                /*System.out.println("cours du jour = "+n.matiere+" le "+n.date);*/
                coursdujour.add(n);
            }
            /*else{
                System.out.println("cours pas correspondant = "+n.matiere+" le "+n.date+" par rapport à "+dateformat);
            }*/
        });
        recyclerViewCours = findViewById(R.id.RecyclerViewCours);
        recyclerViewCours.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewCours.setAdapter(new CustomAdapterCours(coursdujour));


        return_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CoursCalendarActivity.this, CalendarActivity.class );
                startActivity(intent);
            }
        });
    }
}