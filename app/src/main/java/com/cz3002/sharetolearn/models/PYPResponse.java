package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class PYPResponse {
    private String key;
    private ArrayList<User> downvotes;
    private ArrayList<User> upvotes;
    private User postedBy;
    private PYP pyp;
    private String working;
    private String answer;
    private Timestamp postedDateTime;

    public PYPResponse() {
    }

    public PYPResponse(String key, User postedBy, PYP pyp, String working, String answer, Timestamp postedDateTime) {
        this.key = key;
        this.downvotes = new ArrayList<User>();
        this.upvotes = new ArrayList<User>();
        this.postedBy = postedBy;
        this.pyp = pyp;
        this.working = working;
        this.answer = answer;
        this.postedDateTime = postedDateTime;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public ArrayList<User> getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(ArrayList<User> downvotes) {
        this.downvotes = downvotes;
    }

    public void addDownvote(User user) {
        this.downvotes.add(user);
    }


    public ArrayList<User> getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(ArrayList<User> upvotes) {
        this.upvotes = upvotes;
    }

    public void addUpvote(User user) {
        this.upvotes.add(user);
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
}
