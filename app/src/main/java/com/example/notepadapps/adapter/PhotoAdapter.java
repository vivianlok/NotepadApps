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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepadapps.NotesActivity;
import com.example.notepadapps.NotesDetailActivity;
import com.example.notepadapps.R;
import com.example.notepadapps.database.NotesFirebaseItems;
import com.example.notepadapps.database.PhotosFirebaseItems;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


import java.util.List;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

    //Firebase
    private FirebaseUser currentUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference  photoAlbumReference;


    Activity activity;
    List<PhotosFirebaseItems> PhotosFirebaseItemsList;
    View view;


    public PhotoAdapter(Activity activity, List<PhotosFirebaseItems> PhotosFirebaseItems) {

        this.PhotosFirebaseItemsList = PhotosFirebaseItems;
        this.activity = activity;

        firebaseDatabase  = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();
        photoAlbumReference = firebaseDatabase.getReference().child("Albums");




    } //End of NotesAdapter Constructor


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photos, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    } //End of onCreateViewHolder


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PhotosFirebaseItems photosFirebaseItems
                = PhotosFirebaseItemsList.get(position);

        if (photosFirebaseItems.getPhotoUri() != null) {

            if (currentUser != null){

            Picasso.get()
                    .load((photosFirebaseItems.getPhotoUri()))
                    .into(holder.photoImage);
        }
        }

        holder.timeTV.setText(currentUser.getUid());


    } // End of onBindViewHolder


    @Override
    public int getItemCount() {

        return PhotosFirebaseItemsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        // Declare properties for question items
        public ImageView photoImage;
        public  TextView timeTV;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public ViewHolder(View itemView) {

            super(itemView);


            // Initialize all properties

            photoImage = itemView.findViewById(R.id.photoImage);
            timeTV = itemView.findViewById(R.id.timeTV);
        }
    } // End of ViewHolder extends RecyclerView.ViewHolder
}