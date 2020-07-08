package com.example.notepadapps.database;

public class VideosFirebaseItems {
    String videoID, videoUri;

    public VideosFirebaseItems(){}

    public VideosFirebaseItems(String videoID, String videoUri) {
        this.videoID = videoID;
        this.videoUri = videoUri;
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
}
