package com.example.esme;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    private final String TAG = null;
    public static String name,emailAdress,userName;
    private double startTime;
    public static Eleve eleve;
    public static boolean premierLancement=true,moodleInstalled=false;

    TextView textViewDate,textViewUser,textViewOK;
    Button btDisc;
    ImageButton btCal,btNotes,btDevoirs,btMoodle;

    public static FirebaseFirestore db;
    public static FirebaseAuth mAuth;
    public static FirebaseUser user;

    private SimpleDateFormat sdfJour = new SimpleDateFormat("EEEE dd MM yyyy HH:mm:ss", Locale.FRANCE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        textViewDate = findViewById(R.id.textViewDate);
        textViewUser = findViewById(R.id.textViewUser);
        textViewOK = findViewById(R.id.textViewOK);

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
                                if(premierLancement&&System.currentTimeMillis()-startTime>200&&System.currentTimeMillis()-startTime<1200){
                                    getCours(eleve);
                                    getDevoirs(eleve);
                                    updateUI();
                                    premierLancement=false;
                                }
                            }
                        });
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
        btCal = findViewById(R.id.calendar_image_button);
        btCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        btNotes = findViewById(R.id.notes_image_button);
        btNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(intent);
            }
        });
        btDevoirs = findViewById(R.id.devoirs_image_button);
        btDevoirs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DevoirsActivity.class);
                startActivity(intent);
            }
        });

        btMoodle = findViewById(R.id.moodle_image_button);
        btMoodle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(moodleInstalled){
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.moodle.moodlemobile");
                    if (launchIntent != null) {
                        startActivity(launchIntent);
                    }
                }
                else{
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://moodle.esme.fr"));
                    startActivity(browserIntent);
                }
            }
        });

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(premierLancement){
            btDevoirs.setEnabled(false);
            btCal.setEnabled(false);
            btNotes.setEnabled(false);
            eleve = new Eleve();
            createEleve(user);
            getNotes(user);
        }
        userName = user.getDisplayName();
        emailAdress = user.getEmail();
        textViewUser.setText(userName + "\n" + emailAdress);

        //com.moodle.moodlemobile
        PackageManager pm = this.getPackageManager();
        moodleInstalled=isPackageInstalled("com.moodle.moodlemobile",pm);
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
                        eleve.classe=document.getString("classe");
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
                        Log.d(TAG, "note "+document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    private void getCours(Eleve eleve){
        db.collection("classes")
                .document(eleve.classe).collection("emploidutemps")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        eleve.emploidutemps.add(document.toObject(Cours.class));
                        Log.d(TAG, "cours "+document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    private void getDevoirs(Eleve eleve){
        db.collection("classes")
                .document(eleve.classe).collection("devoirs")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        eleve.devoirs.add(document.toObject(Devoir.class));
                        Log.d(TAG, "devoir "+document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
    @SuppressLint("NewApi")
    private void updateUI(){
        btDevoirs.setEnabled(true);
        btCal.setEnabled(true);
        btNotes.setEnabled(true);
        textViewOK.setText("OK");
    }
    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}