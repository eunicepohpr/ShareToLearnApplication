package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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


    // get firestore format to add
    public Map<String, Object> getFireStoreFormat() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> disResDocData = new HashMap<>();
        disResDocData.put("answer", this.answer);
        disResDocData.put("discussion", db.collection("Discussion").document(this.discussionKey));
        disResDocData.put("downvotes", this.getReferenceListFireStoreFormat(this.downvoteKeys, "User"));
        disResDocData.put("postedBy", db.collection("User").document(this.postedByKey));
        disResDocData.put("postedDateTime", new Timestamp(this.postedDateTime));
        disResDocData.put("upvotes", this.getReferenceListFireStoreFormat(this.upvoteKeys, "User"));
        return disResDocData;
    }

    // format string into firestore document reference format
    public ArrayList<DocumentReference> getReferenceListFireStoreFormat(ArrayList<String> list, String collection) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<DocumentReference> docList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++)
            docList.add(db.collection(collection).document(list.get(i)));
        return docList;
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

    @Override
    public int hashCode(){
        if (key != null)
            return key.hashCode();
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o){
        DiscussionResponse response;
        if (!(o instanceof DiscussionResponse))
            return false;
        response = (DiscussionResponse) o;
        return key.equals(response.getKey());
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
