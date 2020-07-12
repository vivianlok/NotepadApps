package com.example.notepadapps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notepadapps.database.CommentsFirebaseItems;
import com.example.notepadapps.database.YoutubeVideosFirebaseItems;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class YoutubeUploadVideoActivity extends AppCompatActivity {
    // Declare ids from activity_youtube_upload_video.xml
    Button submitYoutubeVideoButton;
    Button attachmentButton;
    EditText videoTitleET,youtubeDescriptionET;
    //
    List<YoutubeVideosFirebaseItems> youtubeVideosFirebaseItemsList = new ArrayList<>();

    //Firebase - Declare Firebase currentUser, Auth for authentication, database reference, also app and storage
    private FirebaseUser currentUser;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference youtubeVideoAlbumReference;

    private FirebaseApp app;
    private FirebaseStorage storage;

    Uri youtube_dataUri = null;

    //Progress Dialog
    //ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_upload_video);

        //Initialize IDs
        submitYoutubeVideoButton = findViewById(R.id.submitYoutubeVideoButton);
        attachmentButton = findViewById(R.id.attachmentButton);
        videoTitleET = findViewById(R.id.videoTitleET);
        youtubeDescriptionET = findViewById(R.id.youtubeDescriptionET);

        //Initialize firebase database, current user
        firebaseDatabase  = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();
        youtubeVideoAlbumReference = firebaseDatabase.getReference().child("Youtube_Videos");

        app = FirebaseApp.getInstance();
        storage =FirebaseStorage.getInstance(app);

        //WHEN clicking attachmentButton
        attachmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when attachment is clicked
                selectAttachment();
            }
        }); //End of attachmentButton

        //When you click Submit
        submitYoutubeVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                performActionForSubmitYoutubeVideoButton();
            }
        });

    } //End of onCreate


    public void  performActionForSubmitYoutubeVideoButton() {
        //Declaring toast in case any fields are empty
        if(TextUtils.isEmpty(videoTitleET.getText().toString())){
            Toast.makeText(YoutubeUploadVideoActivity.this,"Error: Please enter a video Title!",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(youtubeDescriptionET.getText().toString())){
            Toast.makeText(YoutubeUploadVideoActivity.this,"Error: Please post description",Toast.LENGTH_SHORT).show();
        }
        else  {

            //First time declaring youtubeVideoID - youtubeVideoID is the snapshot of the database  - youtubeVideoID
            final String youtubeVideoID = youtubeVideoAlbumReference.push().getKey();

            if (youtube_dataUri != null){
                StorageReference storageReference = storage.getReference("Youtube_Videos").child(youtube_dataUri.getLastPathSegment()); //getting pic - last path of image
                storageReference.putFile(youtube_dataUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        attachmentButton.setText("Attachment Added");
                        attachmentButton.setTextColor(Color.DKGRAY);
                    }
                });

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String   downloadedUriForAttachment = uri.toString();
                        saveYoutubeVideoToDatabase(youtubeVideoID, downloadedUriForAttachment);
                    }
                });
            } else {

                saveYoutubeVideoToDatabase(youtubeVideoID, "");

            }

        }
    } //End of When clicking submit public void method ending

    private void saveYoutubeVideoToDatabase(String youtubeVideoID, String attachment) {

        //  String youtubeVideoID, youtubeVideoUri, youtubeVideoTitleTextView,youtubeVideoDescriptionTextView;

        YoutubeVideosFirebaseItems youtubeVideosFirebaseItems
                = new YoutubeVideosFirebaseItems(
                youtubeVideoID,
                attachment,
                videoTitleET.getText().toString(),
                youtubeDescriptionET.getText().toString()
        ); //End of saveYoutubeVideoToDatabase

        youtubeVideoAlbumReference.child(youtubeVideoID)
                .setValue(youtubeVideosFirebaseItems)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        videoTitleET.setText("");
                        youtubeDescriptionET.setText("");
                        showToast("Question successfully submitted.");
                        finish();
                    }
                });
    } //End of this useless method - set title and description to empty string

    public void  showToast(String text){

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    } //End of showToast

    private void selectAttachment() {
        Intent attachment = new Intent(Intent.ACTION_GET_CONTENT);
        attachment.setType("*/*");
        startActivityForResult(Intent.createChooser(attachment, "Pick Attachment"),
                100);
    }//Attachment Method end

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 100 && resultCode == RESULT_OK) {


            youtube_dataUri = data.getData(); //getting path

            attachmentButton.setText("Selected");
            attachmentButton.setTextColor(Color.BLUE);

            //progressDialog.show();

            if (youtube_dataUri != null){
                StorageReference storageReference = storage
                        .getReference("Youtube_Videos")
                        .child(youtube_dataUri.getLastPathSegment()); //getting pic - last path of image
                storageReference.putFile(youtube_dataUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        attachmentButton.setText("Selected");
                        attachmentButton.setTextColor(Color.BLUE);
                    }
                });

                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String  youtubeVideoUri = uri.toString();
                        String youtubeVideoID = youtubeVideoAlbumReference.child(currentUser.getUid()).push().getKey();
                        String youtubeVideoTitleTextView = videoTitleET.toString();
                        String youtubeVideoDescriptionTextView = youtubeDescriptionET.toString();

                        YoutubeVideosFirebaseItems youtubeVideosFirebaseItems
                                = new YoutubeVideosFirebaseItems(
                                youtubeVideoID,
                                youtubeVideoUri,
                                youtubeVideoTitleTextView,
                                youtubeVideoDescriptionTextView
                        );

                        youtubeVideoAlbumReference
                                .child(currentUser.getUid())
                                .child(youtubeVideoID)
                                .setValue(youtubeVideosFirebaseItems)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(YoutubeUploadVideoActivity.this,
                                                "DOne Successfully!", Toast.LENGTH_SHORT).show();
                                        //progressDialog.dismiss();
                                    }
                                });
                    }
                }); //End of   storageReference.getDownloadUrl().addOnSuccessListener
            }


        }

    }//onActivityResult end
} //End of YoutubeUploadVideoActivity Class