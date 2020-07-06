package com.example.notepadapps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.notepadapps.database.NotesFirebaseItems;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //1- Declare Firebase
    //Auth
    private FirebaseUser user;
    private FirebaseAuth FirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference notesDatabaseReference;
    List<NotesFirebaseItems> notesFirebaseItemList = new ArrayList<>();
    Button SubmitButton;
    Button goToNotesButton;
    EditText descriptionET;
    String userID;
    String repliedMessage = " ";
    String notesID;
    //    public static EditText descriptionET;
    String currentDate, currentTime;
    public static final int RC_SIGN_IN = 1;
    //final String notesID = notesDatabaseReference.push().getKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1- Declare Firebase - Authentication Initialization
        FirebaseAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        notesDatabaseReference = firebaseDatabase.getReference().child("notes");

        currentDate = new SimpleDateFormat("E, dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());


        descriptionET = findViewById(R.id.descriptionET);
        SubmitButton = findViewById(R.id.SubmitButton);
        goToNotesButton = findViewById(R.id.goToNotesButton);

        if (user != null) {
            userID = user.getUid();
            //setUserDetails();
        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                user = FirebaseAuth.getCurrentUser();
                if (user != null) {
                    /// onSignedInInitialize(user.getDisplayName());
                    userID = user.getUid();
                    //getUsersDetails(user);


                } else {


                    startActivityForResult(

                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setAvailableProviders(Arrays.asList(
                                            //  new AuthUI.IdpConfig.GoogleBuilder().build()
                                            //     new AuthUI.IdpConfig.FacebookBuilder().build(),
                                            //      new AuthUI.IdpConfig.TwitterBuilder().build(),
                                            //    new AuthUI.IdpConfig.GitHubBuilder().build(),
                                            new AuthUI.IdpConfig.EmailBuilder().build()
                                            //      new AuthUI.IdpConfig.PhoneBuilder().build(),
                                            //     new    AuthUI.IdpConfig.AnonymousBuilder().build()
                                    ))
                                    .build(),
                            RC_SIGN_IN);


                    FirebaseAuth.setLanguageCode("en");
// To apply the default app language instead of explicitly setting it.
// auth.useAppLanguage();

                }
            }


        }; //mAuthStateListener end


        goToNotesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAdapterPage();
            }
        });


        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Push the data in
                //notesDatabaseReference.push();
                //performActionForSubmit();
                //go to next page
                final String notesID = notesDatabaseReference.push().getKey();
                NotesFirebaseItems notesFirebaseItems =
                        new NotesFirebaseItems(
                                notesID,
                                descriptionET.getText().toString(),
                                repliedMessage
                        );
                notesDatabaseReference.child(userID).child(notesID).setValue(notesFirebaseItems);
                Intent goToNotesActivity = new Intent(MainActivity.this, NotesActivity.class);
                goToNotesActivity.putExtra("notesID", notesID);
                startActivity(goToNotesActivity);
            }
        });
    } //end of oncreate


//    private void performActionForSubmit() {
//        NotesFirebaseItems notesFirebaseItems=
//                new NotesFirebaseItems(descriptionET.getText().toString());
//        notesDatabaseReference.child("notes").push().setValue(notesFirebaseItems);
//    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.AdapterItem:
                goToAdapterPage();
                return true;
            case R.id.logouttMenuItem:
                FirebaseAuth.getInstance().signOut();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }//End of Menu

    private void goToAdapterPage() {

        Intent goToAdapter = new Intent(MainActivity.this, NotesActivity.class);
        startActivity(goToAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAuth.addAuthStateListener(mAuthStateListener);


    }

    @Override
    public void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            FirebaseAuth.removeAuthStateListener(mAuthStateListener);


        }
    }

}