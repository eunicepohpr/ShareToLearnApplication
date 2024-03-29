package com.cz3002.sharetolearn.models;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Course implements Serializable {
    private String key;
    private String courseCode;
    private ArrayList<String> registeredUserKeys;
    private String title;
    private String description;
    private ArrayList<String> reviewKeys;
    private String courseAssessment;

    public Course() {
    }

    public Course(String key, String courseCode, String title, String description, String courseAssessment) {
        this.key = key;
        this.courseCode = courseCode;
        this.registeredUserKeys = new ArrayList<>();
        this.title = title;
        this.description = description;
        this.reviewKeys = new ArrayList<>();
        this.courseAssessment = courseAssessment;
//        this.chatMessages = new ArrayList<Chat>();
    }


    // get firestore format to add
    public Map<String, Object> getFireStoreFormat() {
        Map<String, Object> courseDocData = new HashMap<>();
        courseDocData.put("courseAssessment", this.courseAssessment);
        courseDocData.put("courseCode", this.courseCode);
        courseDocData.put("description", this.description);
        courseDocData.put("registered", this.getReferenceListFireStoreFormat(this.registeredUserKeys, "User"));
        courseDocData.put("reviews", this.getReferenceListFireStoreFormat(this.reviewKeys, "CourseReview"));
        courseDocData.put("title", this.title);
        return courseDocData;
    }

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


    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCourseAssessment() {
        return courseAssessment;
    }

    public void setCourseAssessment(String courseAssessment) {
        this.courseAssessment = courseAssessment;
    }


    public ArrayList<String> getRegisteredUserKeys() {
        return registeredUserKeys;
    }

    public void setRegisteredUserKeys(ArrayList<String> registeredUserKeys) {
        this.registeredUserKeys = registeredUserKeys;
    }

    public void addRegisteredUserKeys(String userKey) {
        this.registeredUserKeys.add(userKey);
    }


    public ArrayList<String> getReviewKeys() {
        return reviewKeys;
    }

    public void setReviewKeys(ArrayList<String> reviewKeys) {
        this.reviewKeys = reviewKeys;
    }

    public void addReviewKeys(String reviewKey) {
        this.reviewKeys.add(reviewKey);
    }

    @Override
    public boolean equals(Object o) {
        return this.courseCode.equals(((Course) o).getCourseCode());
    }

    @Override
    public int hashCode() {
        if (courseCode != null) return courseCode.hashCode();
        return super.hashCode();
    }
}
