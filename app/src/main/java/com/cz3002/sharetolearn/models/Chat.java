package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;

public class Chat {
    private Course course;
    private Timestamp postedDateTime;
    private String message;
    private User postedBy;

    public Chat(){}

    public Chat(Timestamp postedDateTime, String message, User postedBy) {
        this.postedDateTime = postedDateTime;
        this.message = message;
        this.postedBy = postedBy;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public Timestamp getPostedDateTime() {
        return postedDateTime;
    }

    public void setPostedDateTime(Timestamp postedDateTime) {
        this.postedDateTime = postedDateTime;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public User getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(User postedBy) {
        this.postedBy = postedBy;
    }
}
