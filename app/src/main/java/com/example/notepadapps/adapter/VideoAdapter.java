package com.example.notepadapps.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepadapps.R;
import com.example.notepadapps.VideosDetailActivity;
import com.example.notepadapps.database.PhotosFirebaseItems;
import com.example.notepadapps.database.VideosFirebaseItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    //Firebase
    private FirebaseUser currentUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference  videosAlbumReference;


    Activity activity;
    List<VideosFirebaseItems> VideosFirebaseItemsList;
    View view;


    public VideoAdapter(Activity activity, List<VideosFirebaseItems> VideosFirebaseItems) {

        this.VideosFirebaseItemsList = VideosFirebaseItems;
        this.activity = activity;

        firebaseDatabase  = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();
        videosAlbumReference = firebaseDatabase.getReference().child("Videos");




    } //End of NotesAdapter Constructor


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videos, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    } //End of onCreateViewHolder


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final VideosFirebaseItems videosFirebaseItems
                = VideosFirebaseItemsList.get(position);

        if (videosFirebaseItems.getVideoUri() != null) {

            if (currentUser != null){

                Uri uri = Uri.parse(videosFirebaseItems.getVideoUri());
                holder.videoView.setVideoURI(uri);
                holder.videoView.start();
        }
        }

        if (videosFirebaseItems.getStatus() != null) {

            if (videosFirebaseItems.getStatus().equalsIgnoreCase("watched")) {

                holder.watchedLinearLayout.setVisibility(View.VISIBLE);
            } else {

                holder.watchedLinearLayout.setVisibility(View.GONE);
            }

        }

// set watched
//        videosAlbumReference
//                .child(currentUser.getUid())
//                .child(videosFirebaseItems.getVideoID())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        String status = snapshot.child("status").getValue(String.class);
//
//                        if (status != null){
//
//                            holder.watchedLinearLayout.setVisibility(View.VISIBLE);
//                        } else {
//
//                            holder.watchedLinearLayout.setVisibility(View.GONE);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });

        holder.timeTV.setText(currentUser.getUid());

        holder.replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity, VideosDetailActivity.class);
                intent.putExtra("videoId", videosFirebaseItems.getVideoID());
                intent.putExtra("videoUri", videosFirebaseItems.getVideoUri());
                activity.startActivity(intent);
            }
        });


    } // End of onBindViewHolder


    @Override
    public int getItemCount() {

        return VideosFirebaseItemsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        // Declare properties for question items
        public VideoView videoView;
        public  TextView timeTV;
        public LinearLayout watchedLinearLayout;
        public Button replyButton;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public ViewHolder(View itemView) {

            super(itemView);


            // Initialize all properties

            videoView = itemView.findViewById(R.id.videoView);
            timeTV = itemView.findViewById(R.id.timeTV);
            watchedLinearLayout = itemView.findViewById(R.id.watchedLinearLayout);
            replyButton = itemView.findViewById(R.id.replyButton);
        }
    } // End of ViewHolder extends RecyclerView.ViewHolder
}