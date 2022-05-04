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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DevoirsActivity extends Activity {
    private final String TAG = null;
    public static String name,emailAdress,userName;
    private SimpleDateFormat sdfJour = new SimpleDateFormat("EEEE dd MM yyyy HH:mm:ss", Locale.FRANCE);
    private RecyclerView recyclerViewDevoirs;
    private TextView textViewDate,textViewUser;
    private Button btDisc;
    public static Eleve eleve;
    public static FirebaseAuth mAuth;
    private double startTime;

    @SuppressLint("NewApi")
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

        recyclerViewDevoirs=findViewById(R.id.recyclerViewDevoirs);
        recyclerViewDevoirs.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewDevoirs.setAdapter(new CustomAdapterDevoirs(eleve.devoirs));

        btDisc = findViewById(R.id.btDisconnect2);
        btDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Log.d(TAG,"Utilisateur déconnecté");
                Intent intent = new Intent(DevoirsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });








    }
}