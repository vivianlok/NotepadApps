package com.example.notepadapps;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepadapps.adapter.CommentsAdapter;
import com.example.notepadapps.database.CommentsFirebaseItems;
import com.example.notepadapps.database.PhotosFirebaseItems;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VideosDetailActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference videoAlbumReference;

    String userID;
    List<PhotosFirebaseItems> photosFirebaseItemsList = new ArrayList<>();
    //RecyclerView photoRecyclerView;
    //RecyclerView.Adapter PhotoAdapter;
    ImageView photoImage;
    String videoId, videoUri;
    Intent getAllIntenets;
    TextView postTextView;
    EditText commentEditText;

    List<CommentsFirebaseItems> commentsFirebaseItemsList = new ArrayList<>();
    //List<PhotoCommentsFirebaseItems> photoCommentsFirebaseItemsList = new ArrayList<>();
    RecyclerView commentsRecyclerView;
    //RecyclerView.Adapter PhotoCommentsAdapter;
    RecyclerView.Adapter commentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos_details);

        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        videoAlbumReference = firebaseDatabase.getReference().child("Videos");
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        if (user != null){

            userID = user.getUid();
        }

        photoImage = findViewById(R.id.photoImage);
        postTextView = findViewById(R.id.postTextView);
        commentEditText = findViewById(R.id.commentEditText);
        commentsRecyclerView = findViewById(R.id.commentsRecyclerView);

        LinearLayoutManager noteLinearLayoutManager;
        noteLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        noteLinearLayoutManager.setStackFromEnd(true);

        commentsRecyclerView.setLayoutManager(noteLinearLayoutManager);

        getAllIntenets = getIntent();

        if (getAllIntenets != null){

            videoId = getAllIntenets.getStringExtra("videoId");
            videoUri = getAllIntenets.getStringExtra("videoUri");

           videoAlbumReference
                   .child(userID)
                   .child(videoId)
                   .child("status")
                   .setValue("watched");

            //notesTV.setText(description);

        }

//        postTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//             if (TextUtils.isEmpty(commentEditText.getText().toString())){
//
//                 Toast.makeText(VideosDetailActivity.this, "Please enter comment", Toast.LENGTH_SHORT).show();
//             } else {
//
//                 CommentsFirebaseItems commentsFirebaseItems
//                         = new CommentsFirebaseItems(commentEditText.getText().toString());
////        photoAlbumReference = firebaseDatabase.getReference().child("Albums");
//                 photoAlbumReference
//                         .child(userID)
//                         .child(photoID)
//                         .child("comments")
//                         .push()
//                         .setValue(commentsFirebaseItems)
//                         .addOnSuccessListener(new OnSuccessListener<Void>() {
//                             @Override
//                             public void onSuccess(Void aVoid) {
//
//                                 commentEditText.setText("");
//
//                                 Toast.makeText(VideosDetailActivity.this, "Comments successsfully added!", Toast.LENGTH_SHORT).show();
//                             }
//                         });
//
//             }
//            }
//        });


        attachDatabaseReadListener();


    } // End of onCreate method

    public void attachDatabaseReadListener() {

//
//        photoAlbumReference
//                .child(userID)
//                .child(photoID)
//                .child("comments")
//                .addChildEventListener(new ChildEventListener() {
//                    @Override
//                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                        if (dataSnapshot.exists()) {
//
//                            //noOrderTextView.setVisibility(View.GONE);
//
//                            CommentsFirebaseItems commentsFirebaseItems
//                                    = dataSnapshot.getValue(CommentsFirebaseItems.class);
//
//                            commentsFirebaseItemsList.add(commentsFirebaseItems);
//
//                            // set adapter
//                          commentsAdapter  = new CommentsAdapter(
//                                    VideosDetailActivity.this, commentsFirebaseItemsList);
//                            commentsRecyclerView.setAdapter(commentsAdapter);
//                            commentsAdapter.notifyDataSetChanged();
//
//                        }
//                    }
//
//                    @Override
//                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//                    }
//
//                    @Override
//                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
    } //End of attachDatabaseReadListener Method

} // End of class