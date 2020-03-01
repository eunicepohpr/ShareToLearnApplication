package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class Discussion {
    private String key;
    private Course course;
    private String question;
    private User postedBy;
    private String title;
    private Timestamp postedDateTime;
    private DocumentReference courseKey;
    private DocumentReference postedByKey;
    private ArrayList<DocumentReference> responseKeys;
    private ArrayList<DocumentReference> likeKeys;
//    private ArrayList<User> responses;
//    private ArrayList<User> likes;

    public Discussion() {
    }

    public Discussion(String key, DocumentReference courseKey, String question,
                      DocumentReference postedByKey, String title, Timestamp postedDateTime) {
        this.key = key;
        this.question = question;
        this.title = title;
        this.postedDateTime = postedDateTime;
        this.courseKey = courseKey;
        this.postedByKey = postedByKey;
        this.responseKeys = new ArrayList<>();
        this.likeKeys = new ArrayList<>();
//        this.responses = new ArrayList<>();
//        this.likes = new ArrayList<>();
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


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }


    public Timestamp getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(Timestamp postedDateTime) {
        this.postedDateTime = postedDateTime;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public ArrayList<DocumentReference> getResponseKeys() {
        return responseKeys;
    }

    public void setResponseKeys(ArrayList<DocumentReference> responseKeys) {
        this.responseKeys = responseKeys;
    }

    public void addResponseKey(DocumentReference responseKey) {
        this.responseKeys.add(responseKey);
    }


    public ArrayList<DocumentReference> getLikeKeys() {
        return likeKeys;
    }

    public void setLikeKeys(ArrayList<DocumentReference> likeKeys) {
        this.likeKeys = likeKeys;
    }

    public void addLikeKey(DocumentReference likeKey) {
        this.likeKeys.add(likeKey);
    }


    public DocumentReference getPostedByKey() {
        return postedByKey;
    }

    public void setPostedByKey(DocumentReference postedByKey) {
        this.postedByKey = postedByKey;
    }


    public DocumentReference getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(DocumentReference courseKey) {
        this.courseKey = courseKey;
    }


//    public ArrayList<User> getResponses() {
//        return responses;
//    }
//
//    public void setResponses(ArrayList<User> responses) {
//        this.responses = responses;
//    }
//
//    public void addResponse(User user){
//        this.responses.add(user);
//    }
//
//
//    public ArrayList<User> getLikes() {
//        return likes;
//    }
//
//    public void setLikes(ArrayList<User> likes) {
//        this.likes = likes;
//    }
//
//    public void addLikes(User like){
//        this.likes.add(like);
//    }
}
