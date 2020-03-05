package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PYPResponse implements Serializable {
    private String key;
    private User postedBy;
    private PYP pyp;
    private String working;
    private String answer;
    private Date postedDateTime;
    private String postedByKey;
    private String pypKey;
    private ArrayList<String> downvoteKeys;
    private ArrayList<String> upvoteKeys;
//    private ArrayList<User> downvotes;
//    private ArrayList<User> upvotes;

    public PYPResponse() {
    }

    public PYPResponse(String key, String postedByKey, String pypKey, String working, String answer) {
        this.key = key;
        this.postedByKey = postedByKey;
        this.pypKey = pypKey;
        this.working = working;
        this.answer = answer;
        this.postedDateTime = new Date();
        this.downvoteKeys = new ArrayList<>();
        this.upvoteKeys = new ArrayList<>();
//        this.downvotes = new ArrayList<>();
//        this.upvotes = new ArrayList<>();
    }


    public Map<String, Object> getFireStoreFormat() {
        Map<String, Object> pypResponseDocData = new HashMap<>();
        pypResponseDocData.put("answer", this.answer);
        pypResponseDocData.put("downvotes", this.downvoteKeys);
        pypResponseDocData.put("postedBy", this.postedByKey);
        pypResponseDocData.put("postedDateTime", new Timestamp(this.postedDateTime));
        pypResponseDocData.put("pyp", this.pypKey);
        pypResponseDocData.put("upvotes", this.upvoteKeys);
        pypResponseDocData.put("working", this.working);
        return pypResponseDocData;
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


    public Date getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(Date postedDateTime) {
        this.postedDateTime = postedDateTime;
    }


    public ArrayList<String> getDownvoteKeys() {
        return downvoteKeys;
    }

    public void setDownvoteKeys(ArrayList<String> downvoteKeys) {
        this.downvoteKeys = downvoteKeys;
    }

    public void addDownvoteKey(String downvoteKey) {
        this.downvoteKeys.add(downvoteKey);
    }


    public ArrayList<String> getUpvoteKeys() {
        return upvoteKeys;
    }

    public void setUpvoteKeys(ArrayList<String> upvoteKeys) {
        this.upvoteKeys = upvoteKeys;
    }

    public void addUpvoteKey(String upvoteKey) {
        this.upvoteKeys.add(upvoteKey);
    }


    public String getPostedByKey() {
        return postedByKey;
    }

    public void setPostedByKey(String postedByKey) {
        this.postedByKey = postedByKey;
    }


    public String getPypKey() {
        return pypKey;
    }

    public void setPypKey(String pypKey) {
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
