package com.example.esme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    private final String TAG = null;
    private String name,emailAdress,userName;
    private double startTime;
    public Eleve eleve;
    public ArrayList<Note> listeNotes=new ArrayList<>();

    TextView textViewDate,textViewUser,textViewDb;
    Button btDisc,btUpdate;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private SimpleDateFormat sdfJour = new SimpleDateFormat("EEEE dd MM yyyy HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        
        Button btDisc = findViewById(R.id.btDisconnect);
        Button btcal = findViewById(R.id.btcalendar);
        textViewDate = findViewById(R.id.textViewDate);
        textViewUser = findViewById(R.id.textViewUser);
        textViewDb = findViewById(R.id.textViewDb);

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
                        if(System.currentTimeMillis()-startTime>200&&System.currentTimeMillis()-startTime<1200){
                            updateUI();
                        }
                    }
                }catch(InterruptedException e) {
                }
            }
        };
        threadTemps.start();



        btDisc = findViewById(R.id.btDisconnect);
        btDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                user=null;
                Log.d(TAG,"Utilisateur déconnecté");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }


        });
        btcal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FirebaseUser user=null;
                Intent intent = new Intent(MainActivity.this, Calendarv2.class);
                startActivity(intent);
            }


        });

        btUpdate = findViewById(R.id.btUpdate);
        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        eleve = new Eleve();
        createEleve(user);
        getNotes(user);

        userName = user.getDisplayName();
        emailAdress = user.getEmail();
        textViewUser.setText(userName + "\n" + emailAdress);
        textViewDb.append("\n"+userName + " " + emailAdress);


    }
    private void createEleve(FirebaseUser user){
        db.collection("eleves").document(user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        eleve.nom=document.getString("nom");
                        eleve.prenom=document.getString("prenom");
                        eleve.email=document.getString("email");
                        Log.d(TAG,"entité élève créée : "+eleve.nom);
                        name=eleve.prenom+" "+eleve.nom;
                        if(userName==null){
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    private void getNotes(FirebaseUser user){
        db.collection("eleves").document(user.getEmail()).collection("notes").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        eleve.notes.add(document.toObject(Note.class));
                        //Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    @SuppressLint("NewApi")
    private void updateUI(){
        eleve.notes.forEach(n -> {
            textViewDb.append("\n"+n.matiere+" : "+n.note);
        });
    }
}