package com.example.notepadapps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notepadapps.adapter.PhotoAdapter;
import com.example.notepadapps.database.VideosFirebaseItems;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class VideoActivity extends AppCompatActivity {
    Button UploadVideoButton,goToVideoAlbumButton;

    //Firebase
    private FirebaseUser currentUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference  videoAlbumReference;

    private FirebaseApp app;
    private FirebaseStorage storage;

    Uri dataUri = null;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        firebaseDatabase  = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();
        videoAlbumReference = firebaseDatabase.getReference().child("Videos");

        app = FirebaseApp.getInstance();
        storage =FirebaseStorage.getInstance(app);
        UploadVideoButton = findViewById(R.id.UploadVideoButton);
        goToVideoAlbumButton = findViewById(R.id.goToVideoAlbumButton);

        progressDialog = new ProgressDialog(VideoActivity.this);
        progressDialog.setTitle("Uploading Video");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        UploadVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAttachment();
            }
        });

        goToVideoAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent VideoAlbumActivity = new Intent(VideoActivity.this, VideoAlbumActivity.class);
                startActivity(VideoAlbumActivity);
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

            UploadVideoButton.setText("Selected");
            UploadVideoButton.setTextColor(Color.BLUE);

            progressDialog.show();

            if (dataUri != null){
                StorageReference storageReference =
                        storage
                        .getReference("Videos") //get reference from Videos in Firebase
                        .child(dataUri.getLastPathSegment()); //getting pic - last path of image
                storageReference.putFile(dataUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        UploadVideoButton.setText("Selected");
                        UploadVideoButton.setTextColor(Color.BLUE);
                    }
                });

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String   downloadedUriForVideo = uri.toString();

                        String videoID = videoAlbumReference.child(currentUser.getUid()).push().getKey();

                        VideosFirebaseItems videosFirebaseItems
                                = new VideosFirebaseItems(
                                videoID,
                                downloadedUriForVideo,
                                "not watched"
                        );

                        videoAlbumReference
                                .child(currentUser.getUid())
                                .child(videoID)
                                .setValue(videosFirebaseItems)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(VideoActivity.this,
                                        "DOne Successfully!", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                }); //End of storageReference getDownloadUri
            }


        }

    }//onActivityResult end

    @Override
    public void onBackPressed(){
        if (progressDialog.isShowing()){

            progressDialog.dismiss();
        } else {
            super.onBackPressed();
        }
    }

} //ENd of class