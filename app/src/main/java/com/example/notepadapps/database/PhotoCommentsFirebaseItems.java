package com.example.notepadapps.database;

public class PhotoCommentsFirebaseItems {
    String commentEditText;

    public PhotoCommentsFirebaseItems(String commentEditText) {
        this.commentEditText = commentEditText;
    }

    public String getCommentEditText() {
        return commentEditText;
    }

    public void setCommentEditText(String commentEditText) {
        this.commentEditText = commentEditText;
    }
}
