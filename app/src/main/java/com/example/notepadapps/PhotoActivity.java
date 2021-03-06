package com.example.notepadapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.notepadapps.database.PhotosFirebaseItems;
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
    DatabaseReference  photoAlbumReference;

    private FirebaseApp app;
    private FirebaseStorage storage;
    ProgressDialog progressDialog;
    Uri dataUri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        firebaseDatabase  = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();
        photoAlbumReference = firebaseDatabase.getReference().child("Albums");

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
                        progressDialog.dismiss();
                        String   downloadedUriForPhoto = uri.toString();

                        String photoId = photoAlbumReference.child(currentUser.getUid()).push().getKey();

                        PhotosFirebaseItems photosFirebaseItems
                                = new PhotosFirebaseItems(
                                        photoId,
                                downloadedUriForPhoto
                        );

                        photoAlbumReference
                                .child(currentUser.getUid())
                                .child(photoId)
                                .setValue(photosFirebaseItems)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(PhotoActivity.this,
                                        "DOne Successfully!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }


        }

    }//onActivityResult end

} //ENd of class