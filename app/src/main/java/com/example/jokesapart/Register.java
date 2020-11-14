package com.example.jokesapart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText mUserName,mPassword,mConfirmPassword;
    Button btn_submit;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUserName= findViewById(R.id.UserName);
        mPassword=findViewById(R.id.Password);
        mConfirmPassword=findViewById(R.id.ConfirmPassword);
        btn_submit=(Button)findViewById(R.id.btn_submit);


        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        if(firebaseAuth.getCurrentUser() != null){

            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mUserName.getText().toString().trim();
                String password= mPassword.getText().toString().trim();
                String confirmPassword= mConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mUserName.setError("Email is Required !!! ");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required !!! ");
                    return;
                }
                if (TextUtils.isEmpty(confirmPassword)){
                    mConfirmPassword.setError("Confirm password is Required !!! ");
                    return;
                }
                if (password.length() < 6){
                    mPassword.setError("Password must be greater than or equal to 6 characters");
                }

                progressBar.setVisibility(View.VISIBLE);

                //Register the user

                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this,"User created",Toast.LENGTH_LONG);
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(Register.this,"Error"+ task.getException(),Toast.LENGTH_LONG);
                        }
                    }
                });
            }
        });


    }


}