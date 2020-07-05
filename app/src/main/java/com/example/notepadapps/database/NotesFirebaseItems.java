package com.example.notepadapps.database;

public class NotesFirebaseItems {
    String notesID;
    String descriptionET;
    String repliedMessage;

    public  NotesFirebaseItems(String s){

    }

    public NotesFirebaseItems(String notesID, String descriptionET, String repliedMessage) {
        this.notesID = notesID;
        this.descriptionET = descriptionET;
        this.repliedMessage = repliedMessage;
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
}
