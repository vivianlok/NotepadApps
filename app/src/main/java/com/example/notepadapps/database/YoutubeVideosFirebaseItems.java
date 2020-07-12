package com.example.notepadapps.database;

public class YoutubeVideosFirebaseItems {
    String youtubeVideoID, youtubeVideoUri, youtubeVideoTitleTextView,youtubeVideoDescriptionTextView;

    public YoutubeVideosFirebaseItems(){}

    public YoutubeVideosFirebaseItems(String youtubeVideoID, String youtubeVideoUri, String youtubeVideoTitleTextView, String youtubeVideoDescriptionTextView) {
        this.youtubeVideoID = youtubeVideoID;
        this.youtubeVideoUri = youtubeVideoUri;
        this.youtubeVideoTitleTextView = youtubeVideoTitleTextView;
        this.youtubeVideoDescriptionTextView = youtubeVideoDescriptionTextView;
    }

    public String getYoutubeVideoID() {
        return youtubeVideoID;
    }

    public void setYoutubeVideoID(String youtubeVideoID) {
        this.youtubeVideoID = youtubeVideoID;
    }

    public String getYoutubeVideoUri() {
        return youtubeVideoUri;
    }

    public void setYoutubeVideoUri(String youtubeVideoUri) {
        this.youtubeVideoUri = youtubeVideoUri;
    }

    public String getYoutubeVideoTitleTextView() {
        return youtubeVideoTitleTextView;
    }

    public void setYoutubeVideoTitleTextView(String youtubeVideoTitleTextView) {
        this.youtubeVideoTitleTextView = youtubeVideoTitleTextView;
    }

    public String getYoutubeVideoDescriptionTextView() {
        return youtubeVideoDescriptionTextView;
    }

    public void setYoutubeVideoDescriptionTextView(String youtubeVideoDescriptionTextView) {
        this.youtubeVideoDescriptionTextView = youtubeVideoDescriptionTextView;
    }
}
