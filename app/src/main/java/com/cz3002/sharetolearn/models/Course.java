package com.cz3002.sharetolearn.models;

import java.util.ArrayList;

public class Course {
    private String key;
    private String courseCode;
    private ArrayList<User> registeredUsers;
    private String title;
    private String description;
    private ArrayList<CourseReview> reviews;
    private String courseAssessment;
//    private ArrayList<Chat> chatMessages;

    public Course() {
    }

    public Course(String key, String courseCode, String title,
                  String description, String courseAssessment) {
        this.key = key;
        this.courseCode = courseCode;
        this.registeredUsers = new ArrayList<User>();
        this.title = title;
        this.description = description;
        this.reviews = new ArrayList<CourseReview>();
        this.courseAssessment = courseAssessment;
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


    public ArrayList<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public void setRegisteredUsers(ArrayList<User> registered) {
        this.registeredUsers = registered;
    }

    public void addRegisteredUser(User user) {
        this.registeredUsers.add(user);
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


    public ArrayList<CourseReview> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<CourseReview> reviews) {
        this.reviews = reviews;
    }

    public void addReview(CourseReview review) {
        this.reviews.add(review);
    }


    public String getCourseAssessment() {
        return courseAssessment;
    }

    public void setCourseAssessment(String courseAssessment) {
        this.courseAssessment = courseAssessment;
    }


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
