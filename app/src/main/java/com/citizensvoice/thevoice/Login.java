package com.citizensvoice.thevoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Login extends AppCompatActivity {
    EditText email, password;
    private FirebaseAuth mAuth;
    Button signin;
    TextView forgotPassword, register;
    FirebaseAuth auth;
    String Email, Password;
    PrefrenceManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Checking for first time launch - before calling setContentView()
        //prefManager = new PrefrenceManager(this);
        //if (!prefManager.isFirstTimeLaunch()) {
        //    launchHomeScreen();
        //    finish();
       // }
        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword);
        forgotPassword = findViewById(R.id.textView33);
        register = findViewById(R.id.textView5);
        signin = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

         Email = email.getText().toString();
         Password = password.getText().toString();



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Signin();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View v) {
                forgotPassword();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
    }
    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            startActivity(new Intent(Login.this, Home.class));
            finish();
        }

    }
    public void Signin(){
        if (TextUtils.isEmpty(Email)){
            email.setError("Please enter your email");
            email.requestFocus();
        }
        else if (TextUtils.isEmpty(Password)){
            password.setError("Please enter your password");
            password.requestFocus();
        }
        else if (Email.isEmpty() && Password.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Fields are Empty!", Toast.LENGTH_SHORT).show();
        }
        else if (Email.isEmpty() && Password.isEmpty()){
            auth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                     //   prefManager.storeUserID(task.getResult().getUser().getUid());
                        Toast.makeText(getApplicationContext(), ""+"Success!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Home.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(), ""+"Unsuccessful. Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
        }
    }

    public void forgotPassword(){
        if (TextUtils.isEmpty(Email)){
            email.requestFocus();
        }
        auth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "An Email Sent To You", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), ""+"Email could not be sent.Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}