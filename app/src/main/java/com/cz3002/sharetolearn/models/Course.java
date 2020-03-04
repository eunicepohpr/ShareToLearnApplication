package com.cz3002.sharetolearn.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Course implements Serializable {
    private String key;
    private String courseCode;
    private ArrayList<String> registeredUserKeys;
    private String title;
    private String description;
    private ArrayList<String> reviewKeys;
    private String courseAssessment;
//    private ArrayList<User> registeredUsers;
//    private ArrayList<CourseReview> reviews;
//    private ArrayList<Chat> chatMessages;

    public Course() {
    }

    public Course(String key, String courseCode, String title,
                  String description, String courseAssessment) {
        this.key = key;
        this.courseCode = courseCode;
        this.registeredUserKeys = new ArrayList<>();
        this.title = title;
        this.description = description;
        this.reviewKeys = new ArrayList<>();
        this.courseAssessment = courseAssessment;
//        this.registeredUsers = new ArrayList<>();
//        this.reviews = new ArrayList<>();
//        this.chatMessages = new ArrayList<Chat>();
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


//    public ArrayList<User> getRegisteredUsers() {
//        return registeredUsers;
//    }
//
//    public void setRegisteredUsers(ArrayList<User> registered) {
//        this.registeredUsers = registered;
//    }
//
//    public void addRegisteredUser(User user) {
//        this.registeredUsers.add(user);
//    }
//
//
//    public ArrayList<CourseReview> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(ArrayList<CourseReview> reviews) {
//        this.reviews = reviews;
//    }
//
//    public void addReview(CourseReview review) {
//        this.reviews.add(review);
//    }

//    public ArrayList<Chat> getChatMessages() {
//        return chatMessages;
//    }
//
//    public void setChatMessages(ArrayList<Chat> chatMessages) {
//        this.chatMessages = chatMessages;
//    }
//
//    public void addChatMessages(Chat chat) {
//        this.chatMessages.add(chat);
//    }
}
