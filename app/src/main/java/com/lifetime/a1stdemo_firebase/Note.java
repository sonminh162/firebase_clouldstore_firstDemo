package com.lifetime.a1stdemo_firebase;

import com.google.firebase.firestore.Exclude;

import java.util.List;

public class Note {
    private String documentId;
    private String title;
    private String description;
    private int priority;
    List<String> tags;

    public Note(){}

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    @Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {

        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<String> getTags() {
        return tags;
    }

}
