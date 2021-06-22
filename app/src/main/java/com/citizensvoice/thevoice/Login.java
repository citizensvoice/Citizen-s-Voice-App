package com.citizensvoice.thevoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    TextView email, password;
    private FirebaseAuth mAuth;
    Button button;
    private PrefrenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefManager = new PrefrenceManager(this);
        email = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();
    }

    private boolean validate(){
        boolean isValid=false;

        String user_email = email.getText().toString();
        String user_password = password.getText().toString();

        if (TextUtils.isEmpty(user_email)) {
            email.setError("Please enter email");
            email.requestFocus();
        } else if (TextUtils.isEmpty(user_password)) {
            password.setError("Please enter your password");
            password.requestFocus();
        }
        else
            isValid=true;
        return isValid;
    }

    private void addCLicklistener(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate())
                    registerUserToDatabse(email.getText().toString(), password.getText().toString());
            }
        });

    }
    private void registerUserToDatabse(final String user_email, String user_password) {

        try {
            mAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(Login.this, "Succesfully created user::email is:" + task.getResult().getUser().getEmail(), Toast.LENGTH_SHORT).show();
                    addUserInDatabse(task.getResult().getUser(), user_email);
                }
            });
        } catch (Exception e) {
            Toast.makeText(Login.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();

        }
    }
    private void addUserInDatabse(final FirebaseUser firebaseUser, String user_email){

        String user_type = "citizen";
        User user= new User(user_type, user_email,firebaseUser.getUid());
        FirebaseDatabase.getInstance().getReference().child("all_users").child("citizen")
                .child(firebaseUser.getUid()).setValue(user);
        Toast.makeText(Login.this, "Succesfully added !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Login.this, Home.class));

    }
}