package com.cz3002.sharetolearn.models;

import java.util.ArrayList;

public class User {
    private String key;
    private ArrayList<Course> registeredCourses;
    private ArrayList<Discussion> discussionLikes;
    private ArrayList<PYP> pypLikes;
    private ArrayList<Discussion> discussionRatings;
    private ArrayList<PYP> pypratings;
    private String biography;
    private String email;
    private String courseOfStudy;
    private String expectedYearOfGrad;

    public User() {
    }

    public User(String key, Course[] registered, String biography, String email, String courseOfStudy,
                String expectedYearOfGrad) {
        this.key = key;
        this.registeredCourses = new ArrayList<Course>();
        this.discussionLikes = new ArrayList<Discussion>();
        this.pypLikes = new ArrayList<PYP>();
        this.discussionRatings = new ArrayList<Discussion>();
        this.pypratings = new ArrayList<PYP>();
        this.biography = biography;
        this.email = email;
        this.courseOfStudy = courseOfStudy;
        this.expectedYearOfGrad = expectedYearOfGrad;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public ArrayList<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(ArrayList<Course> registeredCourse) {
        this.registeredCourses = registeredCourse;
    }

    public void addRegisteredCourse(Course course) {
        this.registeredCourses.add(course);
    }


    public ArrayList<Discussion> getDiscussionLikes() {
        return discussionLikes;
    }

    public void setDiscussionLikes(ArrayList<Discussion> discussionLikes) {
        this.discussionLikes = discussionLikes;
    }

    public void addDiscussionLike(Discussion discussion) { this.discussionLikes.add(discussion); }


    public ArrayList<PYP> getPypLikes() {
        return pypLikes;
    }

    public void setPypLikes(ArrayList<PYP> pypLikes) {
        this.pypLikes = pypLikes;
    }

    public void addPypLike(PYP pypLikes) {
        this.pypLikes.add(pypLikes);
    }


    public ArrayList<Discussion> getDiscussionRatings() {
        return discussionRatings;
    }

    public void setDiscussionRatings(ArrayList<Discussion> discussionRatings) {
        this.discussionRatings = discussionRatings;
    }

    public void addDiscussionRating(Discussion discussionRating) {
        this.discussionRatings.add(discussionRating);
    }


    public ArrayList<PYP> getPypratings() {
        return pypratings;
    }

    public void setPypratings(ArrayList<PYP> pypratings) {
        this.pypratings = pypratings;
    }

    public void addPyprating(PYP pyprating) {
        this.pypratings.add(pyprating);
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
}
