package com.lifetime.a1stdemo_firebase;

import com.google.firebase.firestore.Exclude;

import java.util.Map;

public class Note {
    private String documentId;
    private String title;
    private String description;
    private int priority;
    Map<String, Boolean> tags;

    public Note(){}

    public Note(String title, String description, int priority,Map<String, Boolean> tags) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.tags = tags;
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

    public Map<String, Boolean> getTags() {
        return tags;
    }

}
