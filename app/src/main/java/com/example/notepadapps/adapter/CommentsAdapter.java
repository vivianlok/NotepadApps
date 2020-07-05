package com.example.notepadapps.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepadapps.NotesActivity;
import com.example.notepadapps.NotesDetailActivity;
import com.example.notepadapps.R;
import com.example.notepadapps.database.CommentsFirebaseItems;
import com.example.notepadapps.database.NotesFirebaseItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference notesDatabaseReference;
    String userId;
    Activity activity;
    List<CommentsFirebaseItems> commentsFirebaseItemsList;
    View view;


    public CommentsAdapter(Activity activity, List<CommentsFirebaseItems> commentsFirebaseItems) {
        this.commentsFirebaseItemsList = commentsFirebaseItems;
        this.activity = activity;

        firebaseDatabase = FirebaseDatabase.getInstance();
        notesDatabaseReference = firebaseDatabase.getReference().child("notes");

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();


        if (user != null) {

            userId = user.getUid();
        }


    } //End of NotesAdapter Constructor

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    } //End of onCreateViewHolder

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CommentsFirebaseItems commentsFirebaseItems
                = commentsFirebaseItemsList.get(position);


        holder.commentsTextView.setText(commentsFirebaseItems.getCommentEditText());


    } // End of onBindViewHolder




    @Override
    public int getItemCount() {

        return commentsFirebaseItemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Declare properties for question items
        public TextView  commentsTextView;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public ViewHolder(View itemView) {

            super(itemView);


            // Initialize all properties

          commentsTextView = itemView.findViewById(R.id.commentsTextView);


        }
    } // End of ViewHolder extends RecyclerView.ViewHolder
}