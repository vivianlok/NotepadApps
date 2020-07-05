package com.example.notepadapps.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notepadapps.NotesActivity;
import com.example.notepadapps.R;
import com.example.notepadapps.database.NotesFirebaseItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference notesDatabaseReference;
    String userId;
    Activity activity;
    List<NotesFirebaseItems> notesFirebaseItemsList;
    View view;


    public NotesAdapter(Activity activity, List<NotesFirebaseItems> NotesFirebaseItems) {
        this.notesFirebaseItemsList = NotesFirebaseItems;
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

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notes, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    } //End of onCreateViewHolder

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NotesFirebaseItems notesFirebaseItems
                = notesFirebaseItemsList.get(position);

        notesFirebaseItems.getNotesID();

        //Assign holders here
//        holder.notesTV.setText(notesFirebaseItems.getDescriptionET());
//        holder.replyingTV.setText(notesFirebaseItems.getRepliedMessage());
        //holder.notesTV.setText(notesFirebaseItems.getDescriptionET());

        notesMethod(notesFirebaseItems, holder);
        holder.replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //when daily check in is clicked
                //This one is from activity_notes.xml replyET
                goToNotesActivity(notesFirebaseItems);
                NotesActivity.replyEditText.setText("@" + notesFirebaseItems.getDescriptionET());
                //this one comes from item_notes.xml
                //NotesActivity.replyingTV = notesFirebaseItems.getRepliedMessage();
            }
        });
//        holder.replyCardViewLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NotesActivity.replyEditText.setText("@" + notesFirebaseItems.getDescriptionET());
//                NotesActivity.replyingTV = notesFirebaseItems.getRepliedMessage();
//            }
//        });
    } // End of onBindViewHolder

    private void goToNotesActivity(NotesFirebaseItems notesFirebaseItems) {

        /**
         *  Implicit intent to take the users to the replies activity
         */

        Intent intent = new Intent(activity, NotesActivity.class);
        intent.putExtra("notesID", notesFirebaseItems.getNotesID());
        activity.startActivity(intent);
    }


    private void notesMethod(final NotesFirebaseItems notesFirebaseItems, ViewHolder holder) {

        holder.notesTV.setText("Note: " + notesFirebaseItems.getDescriptionET());
        holder.replyingTV.setText("Note: ");

    }

    ; //End of notesMethod


    @Override
    public int getItemCount() {

        return notesFirebaseItemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // Declare properties for question items
        public TextView notesTV,replyingTV;
        //public CardView replyCardViewLayout;
        public Button replyButton;

        @RequiresApi(api = Build.VERSION_CODES.M)
        public ViewHolder(View itemView) {

            super(itemView);


            // Initialize all properties

            notesTV = itemView.findViewById(R.id.notesTV);
            replyingTV = itemView.findViewById(R.id.replyingTV);
//            replyCardViewLayout = itemView.findViewById(R.id.replyCardViewLayout);
            replyButton = itemView.findViewById(R.id.replyButton);


        }
    } // End of ViewHolder extends RecyclerView.ViewHolder
}