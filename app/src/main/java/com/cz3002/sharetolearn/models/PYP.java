package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class PYP {
    private String key;
    private Course course;
    private User postedBy;
    private String question;
    private String title;
    private ArrayList<PYPResponse> responses;
    private ArrayList<User> likes;
    private Timestamp postedDateTime;

    public PYP() {
    }

    public PYP(String key, Course course, User postedBy, String question, String title, Timestamp postedDateTime) {
        this.key = key;
        this.course = course;
        this.postedBy = postedBy;
        this.question = question;
        this.title = title;
        this.responses = new ArrayList<PYPResponse>();
        this.likes = new ArrayList<User>();
        this.postedDateTime = postedDateTime;
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


    public ArrayList<PYPResponse> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<PYPResponse> responses) {
        this.responses = responses;
    }

    public void addResponses(PYPResponse response) {
        this.responses.add(response);
    }


    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public void addLikes(User user) {
        this.likes.add(user);
    }


    public Timestamp getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDate(Timestamp postedDateTime) {
        this.postedDateTime = postedDateTime;
    }
}
