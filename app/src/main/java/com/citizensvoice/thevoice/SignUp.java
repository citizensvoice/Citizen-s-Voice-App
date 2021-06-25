package com.citizensvoice.thevoice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText email, password, fname, lname, pnum;
    private FirebaseAuth mAuth;
    Button button;
    PrefrenceManager prefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        prefManager = new PrefrenceManager(this);
        email = findViewById(R.id.editTextTextEmailAddress);
        button = findViewById(R.id.button3);
        password = findViewById(R.id.editTextTextPersonName6);
        fname = findViewById(R.id.editTextTextPersonName2);
        lname = findViewById(R.id.editTextTextPersonName3);
        pnum = findViewById(R.id.editTextTextPersonName5);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignUp.this, "Success!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUp.this, Home.class));
            }
        });
       // addCLicklistener();
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
                    registerUserToDatabse(email.getText().toString(), password.getText().toString(), fname.getText().toString(),
                            lname.getText().toString(), pnum.getText().toString());
            }
        });

    }
    private void registerUserToDatabse(final String user_email, String user_password, final String FName, final String LName, final String PNum) {

        try {
            mAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Toast.makeText(SignUp.this, "Succesfully created user::email is:" + task.getResult().getUser().getEmail(), Toast.LENGTH_SHORT).show();
                    addUserInDatabse(task.getResult().getUser(), user_email, FName, LName, PNum);
                }
            });
        } catch (Exception e) {
            Toast.makeText(SignUp.this, "Please connect to the internet", Toast.LENGTH_SHORT).show();

        }
    }
    private void addUserInDatabse(final FirebaseUser firebaseUser, String user_email, String FName, String LName, String PNum){
        prefManager.storeUserID(firebaseUser.getUid());
        String user_type = "citizen";
        User user= new User(user_type, user_email,firebaseUser.getUid(), FName, LName, PNum);
        FirebaseDatabase.getInstance().getReference().child("all_users").child("citizen")
                .child(firebaseUser.getUid()).setValue(user);
        Toast.makeText(SignUp.this, "Succesfully added !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(SignUp.this, Home.class));

    }
}