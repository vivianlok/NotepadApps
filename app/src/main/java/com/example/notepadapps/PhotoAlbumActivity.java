package com.example.notepadapps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;

import com.example.notepadapps.adapter.NotesAdapter;
import com.example.notepadapps.adapter.PhotoAdapter;
import com.example.notepadapps.database.NotesFirebaseItems;
import com.example.notepadapps.database.PhotosFirebaseItems;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

public class PhotoAlbumActivity extends AppCompatActivity {

    //1- Declare Firebase
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference photoAlbumReference;

    String userId;
    List<PhotosFirebaseItems> photosFirebaseItemsList = new ArrayList<>();
    RecyclerView photoRecyclerView;
    RecyclerView.Adapter PhotoAdapter;

    private FirebaseApp app;
    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_album);

        photoRecyclerView = findViewById(R.id.photoRecyclerView);
        LinearLayoutManager noteLinearLayoutManager;
        noteLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        noteLinearLayoutManager.setStackFromEnd(true);

        photoRecyclerView.setLayoutManager(noteLinearLayoutManager);

        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        photoAlbumReference = firebaseDatabase.getReference().child("Albums");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        app = FirebaseApp.getInstance();
        storage =FirebaseStorage.getInstance(app);

        photoRecyclerView = findViewById(R.id.photoRecyclerView);

        if (user != null) {

            userId = user.getUid();

            attachDatabaseReadListener();
        }

    } //oncreate end

    public void attachDatabaseReadListener() {
        photoAlbumReference
                .child(userId)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                if (snapshot.exists()) {

                    //noOrderTextView.setVisibility(View.GONE);

                    PhotosFirebaseItems photosFirebaseItems
                            = snapshot.getValue(PhotosFirebaseItems.class);

                    photosFirebaseItemsList.add(photosFirebaseItems);

                    // set adapter
                    PhotoAdapter = new PhotoAdapter(
                            PhotoAlbumActivity.this, photosFirebaseItemsList);
                    photoRecyclerView.setAdapter(PhotoAdapter);
                    PhotoAdapter.notifyDataSetChanged();

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