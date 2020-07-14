package com.example.notepadapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notepadapps.database.CommentsFirebaseItems;
import com.example.notepadapps.database.PhotosFirebaseItems;
import com.example.notepadapps.database.YoutubeVideosFirebaseItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClickedYoutubeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference youtubeVideosReference;

    String userID;
    List<YoutubeVideosFirebaseItems> youtubeVideosFirebaseItemsList = new ArrayList<>();
    String videoId, videoUri;
    Intent getIntents;
    TextView postTextView;
    EditText commentEditText;

    List<CommentsFirebaseItems> commentsFirebaseItemsList = new ArrayList<>();
    RecyclerView commentsRecyclerView;
    RecyclerView.Adapter commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_youtube);

        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        youtubeVideosReference = firebaseDatabase.getReference().child("Youtube_Videos");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if (user != null){

            userID = user.getUid();
        }
        //Declare the video id
        postTextView = findViewById(R.id.postTextView);
        commentEditText = findViewById(R.id.commentEditText);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);

        LinearLayoutManager noteLinearLayoutManager;
        noteLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        noteLinearLayoutManager.setStackFromEnd(true);

        commentsRecyclerView.setLayoutManager(noteLinearLayoutManager);

        getIntents = getIntent();

        if (getIntents != null){

            videoId = getIntents.getStringExtra("videoId");
            videoUri = getIntents.getStringExtra("videoUri");

            youtubeVideosReference
                    .child(videoId)
                    .child("status")
                    .setValue("watched");

            //notesTV.setText(description);

        }
    } //End of onCreate
}//End of ClickedYoutubeActivity Class