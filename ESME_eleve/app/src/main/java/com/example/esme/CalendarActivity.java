package com.example.esme;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends Activity {

    private static final String TAG = "CalendarActivity";
    private SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy", Locale.FRANCE);
    private SimpleDateFormat chg = new SimpleDateFormat("dd/MM/yyyy");
    Date date1 ,datedujour,datecours;
    String datechoisie;
    private RecyclerView recyclerViewCoursImportant;
    public static Eleve eleve;
    private ArrayList<Cours> coursimportant=new ArrayList<>();


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView1);

        try {
            datedujour=new SimpleDateFormat("dd/MM/yyyy").parse(chg.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        eleve=MainActivity.eleve;
        eleve.emploidutemps.forEach(n -> {
            if(n.importance.equals("Important")){

                try {
                    datecours= new SimpleDateFormat("dd/MM/yyyy").parse(n.date);
                    /*System.out.println("Date des cours "+datecours);
                    System.out.println("Date du jour "+datedujour);*/
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                int a = datecours.compareTo(datedujour); // -1 avant , +1 après , 0 même jour

                if (a>=0){
                    coursimportant.add(n);}


            }

        });

        recyclerViewCoursImportant = findViewById(R.id.RVTousCours);
        recyclerViewCoursImportant.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewCoursImportant.setAdapter(new CustomAdapterCours(coursimportant));




        calendarView.setOnDateChangeListener((new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = dayOfMonth+"/"+String.format("%02d",(month+1))+"/"+year;

                try {
                    date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    datechoisie= sdf.format(date1);
                    System.err.println(datechoisie);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.d(TAG,"onSelectedDayChange: mm/dd/yyyy:"+datechoisie);


                Intent intent = new Intent(CalendarActivity.this,CoursCalendarActivity.class);
                intent.putExtra("date",datechoisie);
                intent.putExtra("dateformat",date);
                startActivity(intent);
            }
        }));


    }
}