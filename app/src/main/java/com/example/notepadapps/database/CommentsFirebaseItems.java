package com.example.notepadapps.database;

public class CommentsFirebaseItems {
    String commentEditText;

    public CommentsFirebaseItems(){}

    public CommentsFirebaseItems(String commentEditText) {
        this.commentEditText = commentEditText;
    }

    public String getCommentEditText() {
        return commentEditText;
    }

    public void setCommentEditText(String commentEditText) {
        this.commentEditText = commentEditText;
    }
}
