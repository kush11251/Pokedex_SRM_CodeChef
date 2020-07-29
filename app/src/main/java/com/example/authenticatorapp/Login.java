package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText username,password;
    Button loginButton;
    TextView registerTextView;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username            = findViewById(R.id.username);
        password            = findViewById(R.id.password);
        loginButton         = findViewById(R.id.loginButton);
        registerTextView    = findViewById(R.id.registerTextView);

        fAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String pass = password.getText().toString().trim();
                String uname = username.getText().toString().trim();

                if (TextUtils.isEmpty(uname)) {
                    username.setError("UserName is Required.");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is Required.");
                    return;
                }

                if (pass.length() < 6) {
                    password.setError("Password Must be >= 6 Character");
                }

                fAuth.signInWithEmailAndPassword(uname,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this,"User Logined.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(Login.this,"Error !" + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });
    }
}