package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PYP implements Serializable {
    private String key;
    private Course course;
    private User postedBy;
    private String question;
    private String title;
    private Date postedDateTime;
    private String courseKey;
    private String postedByKey;
    private HashSet<String> responseKeys;
    private HashSet<String> likeKeys;

    public PYP() {
        responseKeys = new HashSet<>();
        likeKeys = new HashSet<>();
    }

    public PYP(String key, String courseKey, String postedByKey, String question, String title,
               Date postedDateTime) {
        this.key = key;
        this.courseKey = courseKey;
        this.postedByKey = postedByKey;
        this.question = question;
        this.title = title;
        this.postedDateTime = postedDateTime;
        this.responseKeys = new HashSet<>();
        this.likeKeys = new HashSet<>();
    }


    // get firestore format to add
    public Map<String, Object> getFireStoreFormat() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> pypDocData = new HashMap<>();
        pypDocData.put("course", db.collection("CourseModule").document(this.courseKey));
        pypDocData.put("likes", this.getReferenceListFireStoreFormat(this.likeKeys, "User"));
        pypDocData.put("postedBy", db.collection("User").document(this.postedByKey));
        pypDocData.put("postedDateTime", new Timestamp(this.postedDateTime));
        pypDocData.put("question", this.question);
        pypDocData.put("responses", this.getReferenceListFireStoreFormat(this.responseKeys, "PYPResponse"));
        pypDocData.put("title", this.title);
        return pypDocData;
    }

    // format string into firestore document reference format
    public ArrayList<DocumentReference> getReferenceListFireStoreFormat(HashSet<String> list, String collection) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<DocumentReference> docList = new ArrayList<>();
        for (String docId: list)
            docList.add(db.collection(collection).document(docId));
        return docList;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Date getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(Date postedDateTime) {
        this.postedDateTime = postedDateTime;
    }


    public HashSet<String> getResponseKeys() {
        return responseKeys;
    }

    public void setResponseKeys(HashSet<String> responseKeys) {
        this.responseKeys = responseKeys;
    }

    public void addResponseKey(String responseKey) {
        this.responseKeys.add(responseKey);
    }


    public HashSet<String> getLikeKeys() {
        return likeKeys;
    }

    public void setLikeKeys(HashSet<String> likeKeys) {
        this.likeKeys = likeKeys;
    }

    public void addLikeKey(String likeKey) {
        this.likeKeys.add(likeKey);
    }


    public String getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }


    public String getPostedByKey() {
        return postedByKey;
    }

    public void setPostedByKey(String postedByKey) {
        this.postedByKey = postedByKey;
    }


    public void removeLikeKey(String key) {
        this.likeKeys.remove(key);
    }

}
