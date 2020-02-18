package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class Discussion {
    private String key;
    private Course course;
    private String question;
    private User postedBy;
    private ArrayList<User> responses;
    private String title;
    private ArrayList<User> likes;
    private Timestamp postedDateTime;

    public Discussion() {
    }

    public Discussion(String key, Course course, String question, User postedBy, String title, Timestamp postedDateTime) {
        this.key = key;
        this.course = course;
        this.question = question;
        this.postedBy = postedBy;
        this.responses = new ArrayList<User>();
        this.title = title;
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


    public ArrayList<User> getResponses() {
        return responses;
    }

    public void setResponses(ArrayList<User> responses) {
        this.responses = responses;
    }

    public void addResponse(User user){
        this.responses.add(user);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public ArrayList<User> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<User> likes) {
        this.likes = likes;
    }

    public void addLikes(User like){
        this.likes.add(like);
    }


    public Timestamp getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(Timestamp postedDateTime) {
        this.postedDateTime = postedDateTime;
    }
}
