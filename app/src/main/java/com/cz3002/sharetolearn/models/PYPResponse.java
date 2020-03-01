package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class PYPResponse {
    private String key;
    private User postedBy;
    private PYP pyp;
    private String working;
    private String answer;
    private Timestamp postedDateTime;
    private DocumentReference postedByKey;
    private DocumentReference pypKey;
    private ArrayList<DocumentReference> downvoteKeys;
    private ArrayList<DocumentReference> upvoteKeys;
//    private ArrayList<User> downvotes;
//    private ArrayList<User> upvotes;

    public PYPResponse() {
    }

    public PYPResponse(String key, DocumentReference postedByKey, DocumentReference pypKey,
                       String working, String answer, Timestamp postedDateTime) {
        this.key = key;
        this.postedByKey = postedByKey;
        this.pypKey = pypKey;
        this.working = working;
        this.answer = answer;
        this.postedDateTime = postedDateTime;
        this.downvoteKeys = new ArrayList<>();
        this.upvoteKeys = new ArrayList<>();
//        this.downvotes = new ArrayList<>();
//        this.upvotes = new ArrayList<>();
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }


    public PYP getPyp() {
        return pyp;
    }

    public void setPyp(PYP pyp) {
        this.pyp = pyp;
    }


    public String getWorking() {
        return working;
    }

    public void setWorking(String working) {
        this.working = working;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public Timestamp getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(Timestamp postedDateTime) {
        this.postedDateTime = postedDateTime;
    }


    public ArrayList<DocumentReference> getDownvoteKeys() {
        return downvoteKeys;
    }

    public void setDownvoteKeys(ArrayList<DocumentReference> downvoteKeys) {
        this.downvoteKeys = downvoteKeys;
    }

    public void addDownvoteKey(DocumentReference downvoteKey) {
        this.downvoteKeys.add(downvoteKey);
    }


    public ArrayList<DocumentReference> getUpvoteKeys() {
        return upvoteKeys;
    }

    public void setUpvoteKeys(ArrayList<DocumentReference> upvoteKeys) {
        this.upvoteKeys = upvoteKeys;
    }

    public void addUpvoteKey(DocumentReference upvoteKey) {
        this.upvoteKeys.add(upvoteKey);
    }


    public DocumentReference getPostedByKey() {
        return postedByKey;
    }

    public void setPostedByKey(DocumentReference postedByKey) {
        this.postedByKey = postedByKey;
    }


    public DocumentReference getPypKey() {
        return pypKey;
    }

    public void setPypKey(DocumentReference pypKey) {
        this.pypKey = pypKey;
    }


//    public ArrayList<User> getDownvotes() {
//        return downvotes;
//    }
//
//    public void setDownvotes(ArrayList<User> downvotes) {
//        this.downvotes = downvotes;
//    }
//
//    public void addDownvote(User user) {
//        this.downvotes.add(user);
//    }
//
//
//    public ArrayList<User> getUpvotes() {
//        return upvotes;
//    }
//
//    public void setUpvotes(ArrayList<User> upvotes) {
//        this.upvotes = upvotes;
//    }
//
//    public void addUpvote(User user) {
//        this.upvotes.add(user);
//    }
}
