package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiscussionResponse implements Serializable {
    private String key;
    private Discussion discussion;
    private User postedBy;
    private String answer;
    private String discussionKey;
    private String postedByKey;
    private ArrayList<String> downvoteKeys;
    private ArrayList<String> upvoteKeys;
    private Date postedDateTime;
//    private ArrayList<User> downvotes;
//    private ArrayList<User> upvotes;

    public DiscussionResponse() {
    }

    public DiscussionResponse(String key, String discussionKey, String postedByKey, String answer,
                              Date postedDateTime) {
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


    public Map<String, Object> getFireStoreFormat() {
        Map<String, Object> disResDocData = new HashMap<>();
        disResDocData.put("answer", this.answer);
        disResDocData.put("discussion", this.discussionKey);
        disResDocData.put("downvotes", this.downvoteKeys);
        disResDocData.put("postedBy", this.postedByKey);
        disResDocData.put("postedDateTime", new Timestamp(this.postedDateTime));
        disResDocData.put("upvotes", this.upvoteKeys);
        return disResDocData;
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

    public void removeDownvoteKey(String downvoteKey) {
        this.downvoteKeys.remove(downvoteKey);
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

    public void removeUpvoteKey(String upvoteKey) {
        this.upvoteKeys.remove(upvoteKey);
    }

    public String getDiscussionKey() {
        return discussionKey;
    }

    public void setDiscussionKey(String discussionKey) {
        this.discussionKey = discussionKey;
    }


    public String getPostedByKey() {
        return postedByKey;
    }

    public void setPostedByKey(String postedByKey) {
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
