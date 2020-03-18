package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class PYPResponse implements Serializable {
    private String key;
    private User postedBy;
    private PYP pyp;
    private String answer;
    private Date postedDateTime;
    private String postedByKey;
    private String pypKey;
    private HashSet<String> downvoteKeys;
    private HashSet<String> upvoteKeys;

    public PYPResponse() {
        this.downvoteKeys = new HashSet<>();
        this.upvoteKeys = new HashSet<>();
    }

    public PYPResponse(String key, String postedByKey, String pypKey, String answer,
                       Date postedDateTime) {
        this.key = key;
        this.postedByKey = postedByKey;
        this.pypKey = pypKey;
        this.answer = answer;
        this.postedDateTime = postedDateTime;
        this.downvoteKeys = new HashSet<>();
        this.upvoteKeys = new HashSet<>();
    }


    // get firestore format to add
    public Map<String, Object> getFireStoreFormat() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> pypResponseDocData = new HashMap<>();
        pypResponseDocData.put("answer", this.answer);
        pypResponseDocData.put("downvotes", this.getReferenceListFireStoreFormat(this.downvoteKeys, "User"));
        pypResponseDocData.put("postedBy", db.collection("User").document(this.postedByKey));
        pypResponseDocData.put("postedDateTime", new Timestamp(this.postedDateTime));
        pypResponseDocData.put("pyp", db.collection("PYP").document(this.pypKey));
        pypResponseDocData.put("upvotes", this.getReferenceListFireStoreFormat(this.upvoteKeys, "User"));
        return pypResponseDocData;
    }

    // format string into firestore document reference format
    public ArrayList<DocumentReference> getReferenceListFireStoreFormat(Collection<String> list, String collection) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<DocumentReference> docList = new ArrayList<>();
        for (String key: list)
            docList.add(db.collection(collection).document(key));
        return docList;
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


    public HashSet<String> getDownvoteKeys() {
        return downvoteKeys;
    }

    public void setDownvoteKeys(HashSet<String> downvoteKeys) {
        this.downvoteKeys = downvoteKeys;
    }

    public void addDownvoteKey(String downvoteKey) {
        this.downvoteKeys.add(downvoteKey);
    }


    public HashSet<String> getUpvoteKeys() {
        return upvoteKeys;
    }

    public void setUpvoteKeys(HashSet<String> upvoteKeys) {
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


    public void removeDownvoteKey(String key) {
        this.downvoteKeys.remove(key);
    }

    public void removeUpvoteKey(String key) {
        this.upvoteKeys.remove(key);
    }

}
