package com.example.esme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NotesActivity extends Activity {
    private final String TAG = null;
    public static String name,emailAdress,userName;
    private SimpleDateFormat sdfJour = new SimpleDateFormat("EEEE dd MM yyyy HH:mm:ss", Locale.FRANCE);
    private RecyclerView recyclerViewNotes;
    private TextView textViewDate,textViewUser;
    private Button btDisc,btRetour;
    public static Eleve eleve;
    public static FirebaseAuth mAuth;
    private double startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_notes);

        textViewDate = findViewById(R.id.textViewDate2);
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
        textViewUser = findViewById(R.id.textViewUser2);

        mAuth = MainActivity.mAuth;
        eleve=MainActivity.eleve;

        userName = eleve.prenom+" "+eleve.nom;
        emailAdress = eleve.email;
        textViewUser.setText(userName + "\n" + emailAdress);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes2);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewNotes.setAdapter(new CustomAdapterNotes(eleve.notes));

        btDisc = findViewById(R.id.btDisconnect2);
        btDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Log.d(TAG,"Utilisateur déconnecté");
                Intent intent = new Intent(NotesActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btRetour=findViewById(R.id.btRetourNotes);
        btRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}