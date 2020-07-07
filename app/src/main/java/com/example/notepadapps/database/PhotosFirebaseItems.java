package com.example.notepadapps.database;

public class PhotosFirebaseItems {
    String photoID, photoUri;

    public PhotosFirebaseItems(){}


    public PhotosFirebaseItems(String photoID, String photoUri) {
        this.photoID = photoID;
        this.photoUri = photoUri;
    }

    public String getPhotoID() {
        return photoID;
    }

    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
