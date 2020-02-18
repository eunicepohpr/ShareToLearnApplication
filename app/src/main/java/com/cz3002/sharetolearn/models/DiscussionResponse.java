package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class DiscussionResponse {
    private String key;
    private Discussion discussion;
    private User postedBy;
    private String answer;
    private ArrayList<User> downvotes;
    private ArrayList<User> upvotes;
    private Timestamp postedDateTime;

    public DiscussionResponse() {
    }

    public DiscussionResponse(String key, Discussion discussion, User postedBy, String answer, Timestamp postedDateTime) {
        this.key = key;
        this.discussion = discussion;
        this.postedBy = postedBy;
        this.answer = answer;
        this.downvotes = new ArrayList<User>();
        this.upvotes = new ArrayList<User>();
        this.postedDateTime = postedDateTime;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public Discussion getDiscussion() {
        return discussion;
    }

    public void setDiscussion(Discussion discussion) {
        this.discussion = discussion;
    }


    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public ArrayList<User> getDownvotes() {
        return downvotes;
    }

    public void setDownvotes(ArrayList<User> downvotes) {
        this.downvotes = downvotes;
    }

    public void addDownvotes(User user){
        this.downvotes.add(user);
    }


    public ArrayList<User> getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(ArrayList<User> upvotes) {
        this.upvotes = upvotes;
    }

    public void addUpvotes(User user){
        this.upvotes.add(user);
    }


    public Timestamp getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(Timestamp postedDateTime) {
        this.postedDateTime = postedDateTime;
    }
}
