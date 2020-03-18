package com.cz3002.sharetolearn.models;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class User implements Serializable {
    private String key;
    private String biography;
    private String email;
    private String courseOfStudy;
    private String expectedYearOfGrad;
    private String name;
    private HashSet<String> registeredCourseKeys;
    private HashSet<String> discussionLikeKeys;
    private HashSet<String> pypLikeKeys;
    private HashSet<String> discussionRatingKeys;
    private HashSet<String> pypRatingKeys;
    private String domain;
    private String imageURL;

    public User() {
    }

    public User(String key, String name){
        this.key = key;
        this.name = name;
    }

    public User(String key, String biography, String email, String courseOfStudy, String expectedYearOfGrad,
                String name, String domain, String imageURL) {
        this.key = key;
        this.biography = biography;
        this.email = email;
        this.courseOfStudy = courseOfStudy;
        this.expectedYearOfGrad = expectedYearOfGrad;
        this.name = name;
        this.registeredCourseKeys = new HashSet<>();
        this.discussionLikeKeys = new HashSet<>();
        this.pypLikeKeys = new HashSet<>();
        this.discussionRatingKeys = new HashSet<>();
        this.pypRatingKeys = new HashSet<>();
        this.domain = domain;
        this.imageURL = imageURL;
    }


    // get firestore format to add
    public Map<String, Object> getFireStoreFormat() {
        Map<String, Object> userDocData = new HashMap<>();
        userDocData.put("biography", this.biography);
        userDocData.put("courseOfStudy", this.courseOfStudy);
        userDocData.put("email", this.email);
        userDocData.put("expectedYearOfGrad", this.expectedYearOfGrad);
        userDocData.put("name", this.name);
        userDocData.put("registered", this.getReferenceListFireStoreFormat(this.registeredCourseKeys, "CourseModule"));
        userDocData.put("domain", this.domain);
        userDocData.put("imageUrl", this.imageURL);

        HashMap<String, ArrayList> likes = new HashMap<>();
        likes.put("discussion", this.getReferenceListFireStoreFormat(this.discussionLikeKeys, "Discussion"));
        likes.put("pyp", this.getReferenceListFireStoreFormat(this.pypLikeKeys, "PYP"));
        userDocData.put("likes", likes);

        HashMap<String, ArrayList> ratings = new HashMap<>();
        ratings.put("discussion", this.getReferenceListFireStoreFormat(this.discussionRatingKeys, "Discussion"));
        ratings.put("pyp", this.getReferenceListFireStoreFormat(this.pypRatingKeys, "PYP"));
        userDocData.put("ratings", ratings);
        return userDocData;
    }

    // format string into firestore document reference format
    public ArrayList<DocumentReference> getReferenceListFireStoreFormat(Collection<String> list, String collection) {
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


    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getCourseOfStudy() {
        return courseOfStudy;
    }

    public void setCourseOfStudy(String courseOfStudy) {
        this.courseOfStudy = courseOfStudy;
    }


    public String getExpectedYearOfGrad() {
        return expectedYearOfGrad;
    }

    public void setExpectedYearOfGrad(String expectedYearOfGrad) {
        this.expectedYearOfGrad = expectedYearOfGrad;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public HashSet<String> getRegisteredCourseKeys() {
        return registeredCourseKeys;
    }

    public void setRegisteredCourseKeys(HashSet<String> registeredCourseKeys) {
        this.registeredCourseKeys = registeredCourseKeys;
    }

    public void addRegisteredCourseKey(String registeredCourseKey) {
        this.registeredCourseKeys.add(registeredCourseKey);
    }


    public HashSet<String> getDiscussionLikeKeys() {
        return discussionLikeKeys;
    }

    public void setDiscussionLikeKeys(HashSet<String> discussionLikeKeys) {
        this.discussionLikeKeys = discussionLikeKeys;
    }

    public void addDiscussionLikeKey(String discussionLikeKey) {
        this.discussionLikeKeys.add(discussionLikeKey);
    }


    public HashSet<String> getPypLikeKeys() {
        return pypLikeKeys;
    }

    public void setPypLikeKeys(HashSet<String> pypLikeKeys) {
        this.pypLikeKeys = pypLikeKeys;
    }

    public void addPypLikeKey(String pypLikeKey) {
        this.pypRatingKeys.add(pypLikeKey);
    }


    public HashSet<String> getDiscussionRatingKeys() {
        return discussionRatingKeys;
    }

    public void setDiscussionRatingKeys(HashSet<String> discussionRatingKeys) {
        this.discussionRatingKeys = discussionRatingKeys;
    }

    public void addDiscussionRatingKey(String discussionRatingKey) {
        this.discussionRatingKeys.add(discussionRatingKey);
    }


    public HashSet<String> getPypRatingKeys() {
        return pypRatingKeys;
    }

    public void setPypRatingKeys(HashSet<String> pypRatingKeys) {
        this.pypRatingKeys = pypRatingKeys;
    }

    public void addPypRatingKey(String pypRatingKey) {
        this.pypRatingKeys.add(pypRatingKey);
    }


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

}
