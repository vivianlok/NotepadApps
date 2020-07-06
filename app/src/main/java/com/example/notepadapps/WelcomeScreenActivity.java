package com.example.notepadapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WelcomeScreenActivity extends AppCompatActivity {
    private FirebaseUser currentUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDetailsReference;

    Button goToNotesButton,goToPhotosButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


        goToNotesButton = findViewById(R.id.goToNotesButton);
        goToPhotosButton = findViewById(R.id.goToPhotosButton);

        goToNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNotesButton = new Intent(WelcomeScreenActivity.this, MainActivity.class);
                startActivity(goToNotesButton);
            }
        });

        goToPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToPhotosButton = new Intent(WelcomeScreenActivity.this, PhotoActivity.class);
                startActivity(goToPhotosButton);
            }
        });


    } // end of onCreateMethod
} //end of class