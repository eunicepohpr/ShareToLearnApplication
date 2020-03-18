package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    public Map<String, Object> getFireStoreFormat() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> chatDocData = new HashMap<>();
        chatDocData.put("course", db.collection("Course").document(this.courseKey));
        chatDocData.put("dateCreated", new Timestamp(this.dateCreated));
        chatDocData.put("messages", chatMessages);

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
