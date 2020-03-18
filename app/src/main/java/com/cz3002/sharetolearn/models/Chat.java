package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Chat implements Serializable {
    private String key;
    private String courseKey;
    private Date dateCreated;
    private ArrayList<ChatMessage> chatMessages;


    public Chat(String key, String courseKey, Date dateCreated) {
        this.key = key;
        this.courseKey = courseKey;
        this.dateCreated = dateCreated;
        this.chatMessages = new ArrayList<>();
    }


    // get firestore format to add
    public HashMap<String, Object> getFireStoreFormat() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> chatDocData = new HashMap<>();
        chatDocData.put("course", db.collection("Course").document(this.courseKey));
        chatDocData.put("dateCreated", new Timestamp(this.dateCreated));
        ArrayList<HashMap<String, Object>> cms = new ArrayList<>();
        for (ChatMessage cm : chatMessages) cms.add(cm.hashFormat());
        chatDocData.put("messages", cms);
        return chatDocData;
    }


    public String getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }


    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void addChatMessages(ChatMessage chatMsg) {
        this.chatMessages.add(chatMsg);
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
