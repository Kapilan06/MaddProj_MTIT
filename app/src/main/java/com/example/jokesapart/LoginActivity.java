package com.example.jokesapart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import java.io.BufferedReader;

public class LoginActivity extends AppCompatActivity {

   EditText userName;
   EditText mPassword;
    Button login, register,btn_dummy;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userName = findViewById(R.id.userName);
        mPassword =  findViewById(R.id.password);
        login = findViewById(R.id.login);



        progressBar=  findViewById(R.id.progressBar2);
        firebaseAuth= FirebaseAuth.getInstance();




        register=(Button)findViewById(R.id.Register);
        register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(getApplicationContext(),Register.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email = userName.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    userName.setError("Email is Required !!! ");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required !!! ");
                    return;
                }

                if (password.length() < 6) {
                    mPassword.setError("Password must be greater than or equal to 6 characters");
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_LONG);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error" + task.getException(), Toast.LENGTH_LONG);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });


            }
        });
    }

     void openMainActivity() {
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
    }

}