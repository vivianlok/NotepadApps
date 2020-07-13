package com.example.notepadapps;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepadapps.adapter.VideoAdapter;
import com.example.notepadapps.database.VideosFirebaseItems;
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
    DatabaseReference videosAlbumReference;

    String userId;
    List<VideosFirebaseItems> videosFirebaseItemsList = new ArrayList<>();
    RecyclerView videoRecyclerView;
    RecyclerView.Adapter VideoAdapter;

    private FirebaseApp app;
    private FirebaseStorage storage;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_album);

        videoRecyclerView = findViewById(R.id.videoRecyclerView);
        LinearLayoutManager noteLinearLayoutManager;
        noteLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        noteLinearLayoutManager.setStackFromEnd(true);

        videoRecyclerView.setLayoutManager(noteLinearLayoutManager);

        progressDialog = new ProgressDialog(YoutubeAlbumActivity.this);
        progressDialog.setTitle("Loading Video");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);


        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        videosAlbumReference = firebaseDatabase.getReference().child("Videos");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        app = FirebaseApp.getInstance();
        storage =FirebaseStorage.getInstance(app);

        videoRecyclerView = findViewById(R.id.videoRecyclerView);

        if (user != null) {

            userId = user.getUid();

            attachDatabaseReadListener();
        }

    } //oncreate end

    public void attachDatabaseReadListener() {
        videosAlbumReference
                .child(userId)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists()) {

                    //noOrderTextView.setVisibility(View.GONE);

                    VideosFirebaseItems videosFirebaseItems
                            = snapshot.getValue(VideosFirebaseItems.class);

                    videosFirebaseItemsList.add(videosFirebaseItems);

                    // set adapter
                    VideoAdapter = new VideoAdapter(
                            YoutubeAlbumActivity.this, videosFirebaseItemsList);
                    videoRecyclerView.setAdapter(VideoAdapter);
                    VideoAdapter.notifyDataSetChanged();

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