package com.example.esme;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends Activity {

    private static final String TAG = "CalendarActivity";
    public static String name,emailAdress,userName;
    private double startTime;
    private SimpleDateFormat sdfJour = new SimpleDateFormat("EEEE dd MM yyyy HH:mm:ss", Locale.FRANCE);
    private SimpleDateFormat sdf = new SimpleDateFormat("EEE dd MMM yyyy", Locale.FRANCE);
    private SimpleDateFormat chg = new SimpleDateFormat("dd/MM/yyyy");
    Date date1 ,datedujour,datecours;
    String datechoisie;
    private RecyclerView recyclerViewCoursImportant;
    private TextView textViewDate,textViewUser;
    public static FirebaseAuth mAuth;
    public static Eleve eleve;
    private ArrayList<Cours> coursimportant=new ArrayList<>();
    private Button crea_event,btRetour,btDisc;
    private String filepath;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_calendar);

        textViewDate = findViewById(R.id.textViewDate3);
        String currentDate = sdfJour.format(new Date());
        textViewDate.setText(currentDate);
        Thread threadTemps = new Thread(){
            @Override
            public void run(){
                try {
                    startTime = System.currentTimeMillis();
                    while(!isInterrupted()){
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run(){
                                textViewDate.setText(sdfJour.format(new Date(System.currentTimeMillis())));
                            }
                        });
                    }
                }catch(InterruptedException e) {
                }
            }
        };
        threadTemps.start();
        textViewUser = findViewById(R.id.textViewUser3);
        mAuth = MainActivity.mAuth;
        eleve=MainActivity.eleve;
        userName = eleve.prenom+" "+eleve.nom;
        emailAdress = eleve.email;
        textViewUser.setText(userName + "\n" + emailAdress);

        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView1);
        crea_event = (Button) findViewById(R.id.Bt_Crea_Event);

        try {
            datedujour=new SimpleDateFormat("dd/MM/yyyy").parse(chg.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            filepath= getFilesDir().getAbsolutePath();
            FileInputStream fileIn = new FileInputStream(filepath+"/Test_Event.txt");
            //ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            //ArrayList<Cours> listeCours=new ArrayList<>();
            boolean cont = true;
            while (cont) {
                try (ObjectInputStream input = new ObjectInputStream(fileIn)) {
                    Cours obj = (Cours) input.readObject();
                    if (obj != null) {
                        //listeCours.add(obj);
                        eleve.emploidutemps.add(obj);
                    } else {
                        cont = false;
                    }
                }
            }
            //Cours cours = (Cours)objectIn.readObject();
            //System.out.println(cours.intitule);
            /*for(int i=0;i<listeCours.size();i++){
                eleve.emploidutemps.add(listeCours.get(i));
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        eleve.emploidutemps.forEach(n -> {
            System.out.println("cours = "+n.matiere+" le "+n.date);
            if(n.importance.equals("Important")){
                try {
                    datecours= new SimpleDateFormat("dd/MM/yyyy").parse(n.date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int a = datecours.compareTo(datedujour); // -1 avant , +1 après , 0 même jour
                if (a>=0){
                    coursimportant.add(n);
                }
            }

        });

        recyclerViewCoursImportant = findViewById(R.id.RVTousCours);
        recyclerViewCoursImportant.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewCoursImportant.setAdapter(new CustomAdapterCours(coursimportant));

        calendarView.setOnDateChangeListener((new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = String.format("%02d",(dayOfMonth))+"/"+String.format("%02d",(month+1))+"/"+year;

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

        crea_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, NewCoursActivity.class );
                startActivity(intent);
            }
        });
        btDisc = findViewById(R.id.btDisconnect3);
        btDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Log.d(TAG,"Utilisateur déconnecté");
                Intent intent = new Intent(CalendarActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btRetour=findViewById(R.id.btRetourCal);
        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}