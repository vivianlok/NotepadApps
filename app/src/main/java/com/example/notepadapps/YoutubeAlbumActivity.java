package com.example.notepadapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepadapps.adapter.VideoAdapter;
import com.example.notepadapps.adapter.YoutubeVideoAdapter;
import com.example.notepadapps.database.VideosFirebaseItems;
import com.example.notepadapps.database.YoutubeVideosFirebaseItems;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class YoutubeAlbumActivity extends AppCompatActivity {

    //1- Declare Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference youtubeVideoAlbumReference;

    String userId;
    List<YoutubeVideosFirebaseItems> youtubeVideosFirebaseItemsList = new ArrayList<>();
    RecyclerView youtubeVideoRecyclerView;
    RecyclerView.Adapter youtubeVideoAdapter;

    private FirebaseApp app;
    private FirebaseStorage storage;
    ProgressDialog progressDialog;
    Button UploadVideoButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        UploadVideoButton = findViewById(R.id.UploadVideoButton);
        UploadVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToUploadButton = new Intent(YoutubeAlbumActivity.this, YoutubeUploadVideoActivity.class);
                startActivity(goToUploadButton);
            }
        });
        youtubeVideoRecyclerView = findViewById(R.id.youtubeVideoRecyclerView);
        LinearLayoutManager noteLinearLayoutManager;
        noteLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        noteLinearLayoutManager.setStackFromEnd(true);

        youtubeVideoRecyclerView.setLayoutManager(noteLinearLayoutManager);

        progressDialog = new ProgressDialog(YoutubeAlbumActivity.this);
        progressDialog.setTitle("Loading Video");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        youtubeVideoAlbumReference = firebaseDatabase.getReference().child("Youtube_Videos");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        app = FirebaseApp.getInstance();
        storage =FirebaseStorage.getInstance(app);


        if (user != null) {

            userId = user.getUid();

            attachDatabaseReadListener();
        }

    } //oncreate end

    public void attachDatabaseReadListener() {
        youtubeVideoAlbumReference
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        if (snapshot.exists()) {

                            //noOrderTextView.setVisibility(View.GONE);

                            YoutubeVideosFirebaseItems youtubeVideosFirebaseItems
                                    = snapshot.getValue(YoutubeVideosFirebaseItems.class);

                            youtubeVideosFirebaseItemsList.add(youtubeVideosFirebaseItems);

                            // set adapter
                            youtubeVideoAdapter = new YoutubeVideoAdapter(
                                    YoutubeAlbumActivity.this, youtubeVideosFirebaseItemsList);
                            youtubeVideoRecyclerView.setAdapter(youtubeVideoAdapter);
                            youtubeVideoAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    } //attachDatabaseReadListener
} //class end