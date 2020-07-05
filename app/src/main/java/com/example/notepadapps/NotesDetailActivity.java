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

import com.example.notepadapps.adapter.CommentsAdapter;
import com.example.notepadapps.adapter.NotesAdapter;
import com.example.notepadapps.database.CommentsFirebaseItems;
import com.example.notepadapps.database.NotesFirebaseItems;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class NotesDetailActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference notesDatabaseReference;
    String userID;

    Intent getAllIntenets;
    String noteId, description;
    TextView notesTV,postTextView;
    EditText commentEditText;

    List<CommentsFirebaseItems> commentsFirebaseItemsList = new ArrayList<>();
    RecyclerView commentsRecyclerView;
    RecyclerView.Adapter commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_details);

        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        notesDatabaseReference = firebaseDatabase.getReference().child("notes");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if (user != null){

            userID = user.getUid();
        }

        notesTV = findViewById(R.id.notesTV);
        postTextView = findViewById(R.id.postTextView);
        commentEditText = findViewById(R.id.commentEditText);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);

        LinearLayoutManager noteLinearLayoutManager;
        noteLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        noteLinearLayoutManager.setStackFromEnd(true);

        commentsRecyclerView.setLayoutManager(noteLinearLayoutManager);

        getAllIntenets = getIntent();

        if (getAllIntenets != null){

            noteId = getAllIntenets.getStringExtra("notesID");
            description = getAllIntenets.getStringExtra("description");

            notesTV.setText(description);

        }

        postTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             if (TextUtils.isEmpty(commentEditText.getText().toString())){

                 Toast.makeText(NotesDetailActivity.this, "Please enter comment", Toast.LENGTH_SHORT).show();
             } else {

                 CommentsFirebaseItems commentsFirebaseItems
                         = new CommentsFirebaseItems(commentEditText.getText().toString());

                 notesDatabaseReference.child(userID)
                         .child(noteId)
                         .child("comments")
                         .push()
                         .setValue(commentsFirebaseItems)
                         .addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void aVoid) {

                                 commentEditText.setText("");

                                 Toast.makeText(NotesDetailActivity.this, "Comments successsfully added!", Toast.LENGTH_SHORT).show();
                             }
                         });

             }
            }
        });


        attachDatabaseReadListener();


    } // End of onCreate method

    public void attachDatabaseReadListener() {


        notesDatabaseReference
                .child(userID)
                .child(noteId)
                .child("comments")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        if (dataSnapshot.exists()) {

                            //noOrderTextView.setVisibility(View.GONE);

                            CommentsFirebaseItems commentsFirebaseItems
                                    = dataSnapshot.getValue(CommentsFirebaseItems.class);

                            commentsFirebaseItemsList.add(commentsFirebaseItems);

                            // set adapter
                          commentsAdapter  = new CommentsAdapter(
                                    NotesDetailActivity.this, commentsFirebaseItemsList);
                            commentsRecyclerView.setAdapter(commentsAdapter);
                            commentsAdapter.notifyDataSetChanged();

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

} // End of class