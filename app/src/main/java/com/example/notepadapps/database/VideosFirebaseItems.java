package com.example.notepadapps.database;

public class VideosFirebaseItems {
    String videoID, videoUri,status;

    public VideosFirebaseItems(){}

    public VideosFirebaseItems(String videoID, String videoUri, String status) {
        this.videoID = videoID;
        this.videoUri = videoUri;
        this.status = status;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
