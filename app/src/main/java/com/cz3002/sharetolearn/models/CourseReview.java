package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;

class CourseReview {
    private String key;
    private double rating;
    private Timestamp ratedDateTime;
    private User ratedBy;

    public CourseReview() {
    }

    public CourseReview(String key, double rating, Timestamp ratedDateTime, User ratedBy) {
        this.key = key;
        this.rating = rating;
        this.ratedDateTime = ratedDateTime;
        this.ratedBy = ratedBy;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public Timestamp getDate() {
        return ratedDateTime;
    }

    public void setDate(Timestamp ratedDateTime) {
        this.ratedDateTime = ratedDateTime;
    }


    public User getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(User ratedBy) {
        this.ratedBy = ratedBy;
    }
}
