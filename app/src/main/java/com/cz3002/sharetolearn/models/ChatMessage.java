package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;

public class ChatMessage {
    private String message;
    private Date postedDateTime;
    private String postedByKey;

    public ChatMessage(String message, Date postedDateTime, String postedByKey) {
        this.message = message;
        this.postedDateTime = postedDateTime;
        this.postedByKey = postedByKey;
    }


    public HashMap<String, Object> hashFormat() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> hashed = new HashMap<>();
        hashed.put("message", this.message);
        this.postedByKey.substring(0, 12);
        hashed.put("postedBy", db.collection("User").document(this.postedByKey.substring(0, 12)));
        hashed.put("postedDateTime", new Timestamp(this.postedDateTime));
        return hashed;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Date getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(Date postedDateTime) {
        this.postedDateTime = postedDateTime;
    }


    public String getPostedByKey() {
        return postedByKey;
    }

    public void setPostedByKey(String postedByKey) {
        this.postedByKey = postedByKey;
    }
}
