package com.example.esme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends Activity {
    private EditText userEmail;
    private EditText userPassword;
    private Button buttonLogin;
    private CheckBox checkBox;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;
    private String TAG="EmailPassword";
    private String name,emailAdress,filepath;
    private File file_id;
    private Identifiants identifiants;
    private String[] identifiantsEncryptes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        MainActivity.premierLancement=true;

        userEmail = (EditText)findViewById(R.id.user_email);
        userPassword = (EditText)findViewById(R.id.user_password);
        buttonLogin = (Button)findViewById(R.id.button_login);
        checkBox=(CheckBox)findViewById(R.id.checkBoxRemember);

        //userEmail.setText("robin.lotode@esme.fr");
        //userPassword.setText("@ndroid16");

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();
                signIn(email,password);
                //user = FirebaseAuth.getInstance().getCurrentUser();
                //AuthStateListener si besoin
            }
        });
        filepath= getFilesDir().getAbsolutePath()+"/dernierconnect.txt";
        File file = new File(filepath);
        if(file.exists()){
            try {
                FileInputStream fileIn = new FileInputStream(filepath);
                try (ObjectInputStream input = new ObjectInputStream(fileIn)) {
                    Identifiants ident = (Identifiants) input.readObject();
                    if (ident != null) {
                        System.out.println("lecture de "+new Gson().toJson(ident));
                        userEmail.setText(ident.ndc);
                        userPassword.setText(ident.decrypt(ident.mdpenc));
                        checkBox.setChecked(true);
                        input.close();
                        fileIn.close();
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            user = mAuth.getCurrentUser();
                            if (user != null) {
                                name=user.getDisplayName();
                                emailAdress=user.getEmail();
                                Log.d(TAG,"Utilisateur connecté : "+name+" "+email);
                                if(checkBox.isChecked()){
                                    identifiants=new Identifiants(email,password);
                                    try {
                                        FileOutputStream fileOut = new FileOutputStream(filepath);
                                        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
                                        objectOut.writeObject(identifiants);
                                        objectOut.close();
                                        fileOut.close();
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                                else{
                                    File file = new File(filepath);
                                    if (file.exists()){
                                        file.delete();
                                    }
                                }
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            mAuth.signOut();
                        }
                    }
                });
    }
}
