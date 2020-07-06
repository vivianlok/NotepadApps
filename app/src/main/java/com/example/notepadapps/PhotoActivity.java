package com.example.notepadapps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class PhotoActivity extends AppCompatActivity {
    Button UploadPhotoButton,goToPhotoAlbumButton;

    //Firebase
    private FirebaseUser currentUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference usersDetailsReference, questionReference;

    private FirebaseApp app;
    private FirebaseStorage storage;

    Uri dataUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        firebaseDatabase  = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();

        app = FirebaseApp.getInstance();
        storage =FirebaseStorage.getInstance(app);


        UploadPhotoButton = findViewById(R.id.UploadPhotoButton);
        goToPhotoAlbumButton = findViewById(R.id.goToPhotoAlbumButton);

        UploadPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAttachment();
            }
        });

        goToPhotoAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PhotoAlbumActivity = new Intent(PhotoActivity.this, PhotoAlbumActivity.class);
                startActivity(PhotoAlbumActivity);
            }
        });




    } // End of oncreate

    private void selectAttachment() {

        Intent attachment = new Intent(Intent.ACTION_GET_CONTENT);
        attachment.setType("*/*");
        startActivityForResult(Intent.createChooser(attachment, "Pick Attachment"),
                100);

    }//Attachment end

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 100 && resultCode == RESULT_OK) {


            dataUri = data.getData(); //getting path

            UploadPhotoButton.setText("Selected");
            UploadPhotoButton.setTextColor(Color.BLUE);

            if (dataUri != null){
                StorageReference storageReference = storage
                        .getReference("Albums").child(dataUri.getLastPathSegment()); //getting pic - last path of image
                storageReference.putFile(dataUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        UploadPhotoButton.setText("Selected");
                        UploadPhotoButton.setTextColor(Color.BLUE);
                    }
                });

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String   downloadedUriForAttachment = uri.toString();
                    }
                });
            }


        }

    }//onActivityResult end

} //ENd of class