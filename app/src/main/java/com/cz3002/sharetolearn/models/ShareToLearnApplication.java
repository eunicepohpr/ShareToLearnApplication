package com.cz3002.sharetolearn.models;

import java.io.Serializable;
import java.util.HashMap;

public class ShareToLearnApplication implements Serializable {
    private HashMap<String, Course> courses;
    private HashMap<String, User> users;
    private HashMap<String, Discussion> discussions;
    private HashMap<String, PYP> pyps;
    private HashMap<String, CourseReview> courseReviews;
    private HashMap<String, DiscussionResponse> discussionResponses;
    private HashMap<String, PYPResponse> pypResponses;

    public ShareToLearnApplication() {
        courses = new HashMap<>();
        users = new HashMap<>();
        discussions = new HashMap<>();
        pyps = new HashMap<>();
        courseReviews = new HashMap<>();
        discussionResponses = new HashMap<>();
        pypResponses = new HashMap<>();
    }

    public HashMap<String, Course> getCourses() {
        return courses;
    }

    public void setCourses(HashMap<String, Course> courses) {
        this.courses.clear();
        this.courses.putAll(courses);
    }


    public HashMap<String, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String, User> users) {
        this.users.clear();
        this.users.putAll(users);
    }


    public HashMap<String, Discussion> getDiscussions() {
        return discussions;
    }

    public void setDiscussions(HashMap<String, Discussion> discussions) {
        this.discussions.clear();
        this.discussions.putAll(discussions);
    }


    public HashMap<String, PYP> getPyps() {
        return pyps;
    }

    public void setPyps(HashMap<String, PYP> pyps) {
        this.pyps.clear();
        this.pyps.putAll(pyps);
    }


    public HashMap<String, CourseReview> getCourseReviews() {
        return courseReviews;
    }

    public void setCourseReviews(HashMap<String, CourseReview> courseReviews) {
        this.courseReviews.clear();
        this.courseReviews.putAll(courseReviews);
    }


    public HashMap<String, DiscussionResponse> getDiscussionResponses() {
        return discussionResponses;
    }

    public void setDiscussionResponses(HashMap<String, DiscussionResponse> discussionResponses) {
        this.discussionResponses.clear();
        this.discussionResponses.putAll(discussionResponses);
    }


    public HashMap<String, PYPResponse> getPypResponses() {
        return pypResponses;
    }

    public void setPypResponses(HashMap<String, PYPResponse> pypResponses) {
        this.pypResponses.clear();
        this.pypResponses.putAll(pypResponses);
    }
}
