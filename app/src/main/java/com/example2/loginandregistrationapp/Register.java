package com.example2.loginandregistrationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText mFullName,mEmail,mPassword,mPassword2;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName=findViewById(R.id.Email);
        mEmail=findViewById(R.id.Password);
        mPassword=findViewById(R.id.password);
        mPassword2=findViewById(R.id.confirmpassword);
        mRegisterBtn=findViewById(R.id.button);
        mLoginBtn=findViewById(R.id.loginbutton);

        fAuth=FirebaseAuth.getInstance();

        //now if the user is already registered or logged in

        if(fAuth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                String password2=mPassword2.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is required");
                    return ;
                }
                if(TextUtils.isEmpty(password))
                {
                    mPassword.setError("Password is required");
                    return;
                }
                if(password.length()<6)
                {
                    mPassword.setError("Password must be >=6 characters");
                    return;

                }
                if(TextUtils.isEmpty(password2))
                {
                    mPassword2.setError("Please confirm password");
                    return ;
                }
                if(password.equals(password2)==false)
                {
                    mPassword2.setError("Please enter correctly");
                    return;
                }
                //register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { //checking whether task is successful or not
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this, "User registered", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));


                        }
                        else
                        {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }

}