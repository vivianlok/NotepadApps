package com.example.notepadapps.database;

public class NotesFirebaseItems {
    String notesID;
    String descriptionET;
    String repliedMessage;
    String date;
    String time;
    String fullName;

    public  NotesFirebaseItems(){ }

    public NotesFirebaseItems(String notesID, String descriptionET, String repliedMessage, String date, String time, String fullName) {
        this.notesID = notesID;
        this.descriptionET = descriptionET;
        this.repliedMessage = repliedMessage;
        this.date = date;
        this.time = time;
        this.fullName = fullName;
    }

    public String getNotesID() {
        return notesID;
    }

    public void setNotesID(String notesID) {
        this.notesID = notesID;
    }

    public String getDescriptionET() {
        return descriptionET;
    }

    public void setDescriptionET(String descriptionET) {
        this.descriptionET = descriptionET;
    }

    public String getRepliedMessage() {
        return repliedMessage;
    }

    public void setRepliedMessage(String repliedMessage) {
        this.repliedMessage = repliedMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
