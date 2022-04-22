package com.example.esme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    private final String TAG = null;

    TextView textViewDate,textViewUser,textViewDb;
    Button btDisc;


    DatabaseReference database;
    private FirebaseAuth mAuth;

    private SimpleDateFormat sdfJour = new SimpleDateFormat("E dd MM yyyy HH:mm:ss", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        textViewDate = findViewById(R.id.textViewDate);
        textViewUser = findViewById(R.id.textViewUser);
        textViewDb = findViewById(R.id.textViewDb);


        btDisc = findViewById(R.id.btDisconnect);

        String currentDate = sdfJour.format(new Date());
        textViewDate.setText(currentDate);
        Thread t = new Thread(){
            @Override
            public void run(){
                try {
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
        t.start();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String emailAdress = user.getEmail();
            if(name.equals("")){
                name="Nom manquant";
            }
            textViewUser.setText(name+"\n"+emailAdress);
        } else {
            textViewUser.setText("Erreur");
        }

        btDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                FirebaseUser user=null;
                Log.d(TAG,"Utilisateur déconnecté");
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        db.collection("eleves").document("1").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Eleve eleve = document.toObject(Eleve.class);
                        textViewDb.append("\n"+"Elève 1 : "+document.getData().toString()+" : "+eleve.nom+" "+eleve.prenom);
                        textViewDb.append("\nListe d'élèves :");
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        db.collection("eleves")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Eleve eleve = document.toObject(Eleve.class);
                                textViewDb.append("\n"+eleve.nom+" "+eleve.prenom);
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}