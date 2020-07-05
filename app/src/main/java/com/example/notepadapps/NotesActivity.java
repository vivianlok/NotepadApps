package com.example.notepadapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notepadapps.adapter.NotesAdapter;
import com.example.notepadapps.database.NotesFirebaseItems;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity {
    //1- Declare Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference notesDatabaseReference;
    Intent getInfoFromMainActivity;
    public static String notesID;

    //2- Declare userID, currentDate, currentTime, replyingText
    public static String currentDate, currentTime, replyingText = null;
    //3- Declare ReplyET and PostButton
    TextView postTextView;
    public static EditText replyEditText;

    String userId;
    List<NotesFirebaseItems> notesFirebaseItemsList = new ArrayList<>();
    RecyclerView notesRecyclerView;
    RecyclerView.Adapter NotesAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

         getInfoFromMainActivity = getIntent();
        if (getInfoFromMainActivity != null) {
            notesID = getInfoFromMainActivity.getStringExtra("notesID");
        } //End of getting Intent from Main Activity

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        notesDatabaseReference = firebaseDatabase.getReference().child("notes");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        postTextView = findViewById(R.id.postTextView);
        replyEditText = findViewById(R.id.replyEditText);

        LinearLayoutManager noteLinearLayoutManager;
        noteLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        noteLinearLayoutManager.setStackFromEnd(true);

        notesRecyclerView.setLayoutManager(noteLinearLayoutManager);

        if (user != null) {

            userId = user.getUid();
        }
//        postTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (TextUtils.isEmpty(replyEditText.getText().toString())) {
//
//                    Toast.makeText(NotesActivity.this, "Please enter a reply", Toast.LENGTH_SHORT).show();
//                } else {
//
//                    //  notesDatabaseReference = firebaseDatabase.getReference().child("notes");
//                   // String replyId = notesDatabaseReference.child(userId).push().getKey();
//
//                    NotesFirebaseItems notesFirebaseItems
//                            = new NotesFirebaseItems(
//                            notesID,
//                            descriptionET,
//                            repliedMessage
//                           // replyId,
//                            //user.getDisplayName(),
//                           // replyEditText.getText().toString()
//                    );
//
//                    notesDatabaseReference.child("notes").push().setValue(notesFirebaseItems).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//
//                            replyEditText.setText("");
//                        }
//                    });
//                }
//            }
//        }); //End of postTextView onclickListener

        attachDatabaseReadListener();
    }//End of oncreate method



    public void attachDatabaseReadListener() {


                notesDatabaseReference
                .child(userId)
                //.child("replies")
               // .child("noteID")
                //.orderByChild("quantity")
                //.equalTo(8)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        if (dataSnapshot.exists()) {

                            //noOrderTextView.setVisibility(View.GONE);

                            NotesFirebaseItems notesFirebaseItems
                                    = dataSnapshot.getValue(NotesFirebaseItems.class);

                            notesFirebaseItemsList.add(notesFirebaseItems);

                            // set adapter
                            NotesAdapter = new NotesAdapter(
                                    NotesActivity.this, notesFirebaseItemsList);
                            notesRecyclerView.setAdapter(NotesAdapter);
                            NotesAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    } //End of attachDatabaseReadListener Method
}