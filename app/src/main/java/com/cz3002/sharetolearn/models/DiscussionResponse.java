package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class DiscussionResponse {
    private String key;
    private Discussion discussion;
    private User postedBy;
    private String answer;
    private DocumentReference discussionKey;
    private DocumentReference postedByKey;
    private ArrayList<DocumentReference> downvoteKeys;
    private ArrayList<DocumentReference> upvoteKeys;
    private Timestamp postedDateTime;
//    private ArrayList<User> downvotes;
//    private ArrayList<User> upvotes;

    public DiscussionResponse() {
    }

    public DiscussionResponse(String key, DocumentReference discussionKey, DocumentReference postedByKey,
                              String answer, Timestamp postedDateTime) {
        this.key = key;
        this.discussionKey = discussionKey;
        this.postedByKey = postedByKey;
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


    public DocumentReference getDiscussionKey() {
        return discussionKey;
    }

    public void setDiscussionKey(DocumentReference discussionKey) {
        this.discussionKey = discussionKey;
    }


    public DocumentReference getPostedByKey() {
        return postedByKey;
    }

    public void setPostedByKey(DocumentReference postedByKey) {
        this.postedByKey = postedByKey;
    }


//    public ArrayList<User> getDownvotes() {
//        return downvotes;
//    }
//
//    public void setDownvotes(ArrayList<User> downvotes) {
//        this.downvotes = downvotes;
//    }
//
//    public void addDownvotes(User user){
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
//    public void addUpvotes(User user){
//        this.upvotes.add(user);
//    }
}
