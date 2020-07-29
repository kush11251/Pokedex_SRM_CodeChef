package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText username,password,emailid;
    Button registerButton;
    TextView loginTextView;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username          = findViewById(R.id.username);
        password          = findViewById(R.id.password);
        emailid           = findViewById(R.id.emailid);
        registerButton    = findViewById(R.id.registerButton);
        loginTextView     = findViewById(R.id.loginTextView);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailid.getText().toString().trim();
                String pass = password.getText().toString().trim();
                final String uname = username.getText().toString().trim();

                if (TextUtils.isEmpty(uname)) {
                    username.setError("UserName is Required.");
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    password.setError("Password is Required.");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailid.setError("Email is Required.");
                    return;
                }

                if (pass.length() < 6) {
                    password.setError("Password Must be >= 6 Character");
                }

                fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this,"User Created.",Toast.LENGTH_SHORT).show();

                            UserID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("Users").document(UserID);

                            Map<String,Object> user = new HashMap<>();
                            user.put("User Name",uname);
                            user.put("EmailID",email);

                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.i("Info","onSuccess: user Profile is created for " + UserID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Register.this,"Error !" + task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });
    }
}