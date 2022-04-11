package com.example.rest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    private EditText userEmail;
    private EditText userPassword;
    private Button buttonLogin;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = (EditText)findViewById(R.id.user_email);
        userPassword = (EditText)findViewById(R.id.user_password);
        buttonLogin = (Button)findViewById(R.id.button_login);
        buttonRegister = (Button)findViewById(R.id.button_register);

        userEmail.setText("robin.lotode@esme.fr");
        userPassword.setText("@ndroid16");

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jAuth = new JSONObject();
                    jAuth.put("email", userEmail.getText().toString());
                    jAuth.put("password", userPassword.getText().toString());
                    jAuth.put("app", "MNA");

                    Log.v("LoginActivity", userEmail.getText().toString()+" "+userPassword.getText().toString());
                    ConnectionRest connectionRest = new ConnectionRest();
                    connectionRest.setObj(jAuth);
                    connectionRest.setAction("auth");
                    connectionRest.execute("POST");
                    String token = connectionRest.get();


                    if(token.charAt(0)=='{') {
                        Log.v("LoginActivity", token);
                    }else{
                        String tabToken[]=token.split("\\.");
                        byte tableByte[] = Base64.decode(tabToken[1],Base64.DEFAULT);
                        String tokenJson=new String(tableByte,"utf-8");
                        JSONArray jsonArray = new JSONArray(tokenJson);
                        JSONObject objJson = jsonArray.getJSONObject(0);

                        Log.v("TOKEN", token);
                        Log.v("PAYLOAD64", tabToken[1]);
                        Log.v("PAYLOAD", tokenJson);

                        Param.getInstance().setToken(token);
                        Param.getInstance().setUserId(objJson.getInt("usr"));
                        Param.getInstance().setUsername(objJson.getString("name"));

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (InterruptedException  e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}