package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;

public class PYP implements Serializable {
    private String key;
    private Course course;
    private User postedBy;
    private String question;
    private String title;
    private Timestamp postedDateTime;
    private String courseKey;
    private String postedByKey;
    private ArrayList<String> responseKeys;
    private ArrayList<String> likeKeys;
//    private ArrayList<PYPResponse> responses;
//    private ArrayList<User> likes;

    public PYP() {
    }

    public PYP(String key, String courseKey, String postedByKey,
               String question, String title, Timestamp postedDateTime) {
        this.key = key;
        this.courseKey = courseKey;
        this.postedByKey = postedByKey;
        this.question = question;
        this.title = title;
        this.postedDateTime = postedDateTime;
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


    public Timestamp getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDate(Timestamp postedDateTime) {
        this.postedDateTime = postedDateTime;
    }


    public ArrayList<String> getResponseKeys() {
        return responseKeys;
    }

    public void setResponseKeys(ArrayList<String> responseKeys) {
        this.responseKeys = responseKeys;
    }

    public void addResponseKey(String responseKey) {
        this.responseKeys.add(responseKey);
    }


    public ArrayList<String> getLikeKeys() {
        return likeKeys;
    }

    public void setLikeKeys(ArrayList<String> likeKeys) {
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


//    public ArrayList<PYPResponse> getResponses() {
//        return responses;
//    }
//
//    public void setResponses(ArrayList<PYPResponse> responses) {
//        this.responses = responses;
//    }
//
//    public void addResponses(PYPResponse response) {
//        this.responses.add(response);
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
//    public void addLikes(User user) {
//        this.likes.add(user);
//    }
}
