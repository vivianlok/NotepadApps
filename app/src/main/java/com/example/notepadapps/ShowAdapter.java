package com.example.notepadapps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notepadapps.database.NotesFirebaseItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowAdapter extends AppCompatActivity {
    //1- Declare Firebase
//    FirebaseAuth firebaseAuth;
//    FirebaseUser user;
//    FirebaseDatabase firebaseDatabase;
//    DatabaseReference notesDatabaseReference;
    List<NotesFirebaseItems> notesFirebaseItemsList = new ArrayList<>();
    RecyclerView notesRecyclerView;
    RecyclerView.Adapter NotesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_adapter);
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        LinearLayoutManager noteLinearLayoutManager;
        noteLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        noteLinearLayoutManager.setStackFromEnd(true);
        notesRecyclerView.setLayoutManager(noteLinearLayoutManager);

    }

}