package com.cz3002.sharetolearn.models;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String key;
    private String biography;
    private String email;
    private String courseOfStudy;
    private String expectedYearOfGrad;
    private String name;
    private ArrayList<String> registeredCourseKeys;
    private ArrayList<String> discussionLikeKeys;
    private ArrayList<String> pypLikeKeys;
    private ArrayList<String> discussionRatingKeys;
    private ArrayList<String> pypRatingKeys;
//    private ArrayList<Course> registeredCourses;
//    private ArrayList<Discussion> discussionLikes;
//    private ArrayList<PYP> pypLikes;
//    private ArrayList<Discussion> discussionRatings;
//    private ArrayList<PYP> pypRatings;

    public User() {
    }

    public User(String key, String biography, String email, String courseOfStudy, String expectedYearOfGrad,
                String name) {
        this.key = key;
        this.biography = biography;
        this.email = email;
        this.courseOfStudy = courseOfStudy;
        this.expectedYearOfGrad = expectedYearOfGrad;
        this.name = name;
        this.registeredCourseKeys = new ArrayList<>();
        this.discussionLikeKeys = new ArrayList<>();
        this.pypLikeKeys = new ArrayList<>();
        this.discussionRatingKeys = new ArrayList<>();
        this.pypRatingKeys = new ArrayList<>();
//        this.registeredCourses = new ArrayList<>();
//        this.discussionLikes = new ArrayList<>();
//        this.pypLikes = new ArrayList<>();
//        this.discussionRatings = new ArrayList<>();
//        this.pypRatings = new ArrayList<>();
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


    public ArrayList<String> getRegisteredCourseKeys() {
        return registeredCourseKeys;
    }

    public void setRegisteredCourseKeys(ArrayList<String> registeredCourseKeys) {
        this.registeredCourseKeys = registeredCourseKeys;
    }

    public void addRegisteredCourseKey(String registeredCourseKey) {
        this.registeredCourseKeys.add(registeredCourseKey);
    }


    public ArrayList<String> getDiscussionLikeKeys() {
        return discussionLikeKeys;
    }

    public void setDiscussionLikeKeys(ArrayList<String> discussionLikeKeys) {
        this.discussionLikeKeys = discussionLikeKeys;
    }

    public void addDiscussionLikeKey(String discussionLikeKey) {
        this.discussionLikeKeys.add(discussionLikeKey);
    }


    public ArrayList<String> getPypLikeKeys() {
        return pypLikeKeys;
    }

    public void setPypLikeKeys(ArrayList<String> pypLikeKeys) {
        this.pypLikeKeys = pypLikeKeys;
    }

    public void addPypLikeKey(String pypLikeKey) {
        this.pypRatingKeys.add(pypLikeKey);
    }


    public ArrayList<String> getDiscussionRatingKeys() {
        return discussionRatingKeys;
    }

    public void setDiscussionRatingKeys(ArrayList<String> discussionRatingKeys) {
        this.discussionRatingKeys = discussionRatingKeys;
    }

    public void addDiscussionRatingKey(String discussionRatingKey) {
        this.discussionRatingKeys.add(discussionRatingKey);
    }


    public ArrayList<String> getPypRatingKeys() {
        return pypRatingKeys;
    }

    public void setPypRatingKeys(ArrayList<String> pypRatingKeys) {
        this.pypRatingKeys = pypRatingKeys;
    }

    public void addPypRatingKey(String pypRatingKey) {
        this.pypRatingKeys.add(pypRatingKey);
    }


//    public ArrayList<Course> getRegisteredCourses() {
//        return registeredCourses;
//    }
//
//    public void setRegisteredCourses(ArrayList<Course> registeredCourse) {
//        this.registeredCourses = registeredCourse;
//    }
//
//    public void addRegisteredCourse(Course course) {
//        this.registeredCourses.add(course);
//    }
//
//
//    public ArrayList<Discussion> getDiscussionLikes() {
//        return discussionLikes;
//    }
//
//    public void setDiscussionLikes(ArrayList<Discussion> discussionLikes) {
//        this.discussionLikes = discussionLikes;
//    }
//
//    public void addDiscussionLike(Discussion discussion) {
//        this.discussionLikes.add(discussion);
//    }
//
//
//    public ArrayList<PYP> getPypLikes() {
//        return pypLikes;
//    }
//
//    public void setPypLikes(ArrayList<PYP> pypLikes) {
//        this.pypLikes = pypLikes;
//    }
//
//    public void addPypLike(PYP pypLikes) {
//        this.pypLikes.add(pypLikes);
//    }
//
//
//    public ArrayList<Discussion> getDiscussionRatings() {
//        return discussionRatings;
//    }
//
//    public void setDiscussionRatings(ArrayList<Discussion> discussionRatings) {
//        this.discussionRatings = discussionRatings;
//    }
//
//    public void addDiscussionRating(Discussion discussionRating) {
//        this.discussionRatings.add(discussionRating);
//    }
//
//
//    public ArrayList<PYP> getPypratings() {
//        return pypRatings;
//    }
//
//    public void setPypratings(ArrayList<PYP> pypratings) {
//        this.pypRatings = pypratings;
//    }
//
//    public void addPyprating(PYP pyprating) {
//        this.pypRatings.add(pyprating);
//    }
}
