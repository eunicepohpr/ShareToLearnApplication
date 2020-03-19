package com.cz3002.sharetolearn.models;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ChatMessage {
    private String message;
    private String postedByKey;

    public ChatMessage(String message, String postedByKey) {
        this.message = message;
        this.postedByKey = postedByKey;
    }


    public HashMap<String, Object> hashFormat() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> hashed = new HashMap<>();
        hashed.put("message", this.message);
//        Log.d("Test", this.postedByKey); // /User/bgNcY2NTgObtT5vyBeLvdf9jJGh1
//        Log.d("Test", this.postedByKey.substring(6)); // bgNcY2NTgObtT5vyBeLvdf9jJGh1
//        hashed.put("postedBy", db.collection("User").document(this.postedByKey.substring(6)));
        hashed.put("postedBy", this.postedByKey);
        return hashed;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


//    public Date getPostedDateTime() {
//        return postedDateTime;
//    }
//
//    public void setPostedDateTime(Date postedDateTime) {
//        this.postedDateTime = postedDateTime;
//    }


    public String getPostedByKey() {
        return postedByKey;
    }

    public void setPostedByKey(String postedByKey) {
        this.postedByKey = postedByKey;
    }

    @Override
    public boolean equals(Object o){
        ChatMessage cm;
        if (!(o instanceof ChatMessage)) return false;
        cm = (ChatMessage) o;
        return cm.getMessage().equals(this.message) && cm.getPostedByKey().equals(this.postedByKey);
    }
}
