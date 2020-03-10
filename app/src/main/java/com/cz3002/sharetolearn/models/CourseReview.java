package com.cz3002.sharetolearn.models;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CourseReview implements Serializable {
    private String key;
    private double rating;
    private Date ratedDateTime;
    private User ratedBy;
    private String description;
    private String courseKey;
    private String ratedByKey;

    public CourseReview() {
    }

    public CourseReview(double rating, String ratedByKey, String description, String courseKey, Date ratedDateTime) {
        this.rating = rating;
        this.ratedByKey = ratedByKey;
        this.description = description;
        this.courseKey = courseKey;
        this.ratedDateTime = ratedDateTime;
    }

    public CourseReview(String key, double rating, Date ratedDateTime, String ratedByKey,
                        String description, String courseKey) {
        this.key = key;
        this.rating = rating;
        this.ratedDateTime = ratedDateTime;
        this.ratedByKey = ratedByKey;
        this.description = description;
        this.courseKey = courseKey;
    }

    public CourseReview(String key, double rating, Date ratedDateTime, String ratedByKey, String description) {
        this.key = key;
        this.rating = rating;
        this.ratedDateTime = ratedDateTime;
        this.ratedByKey = ratedByKey;
        this.description = description;
    }


    public Map<String, Object> getFireStoreReviewFormat() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> courseReviewDocData = new HashMap<>();
        courseReviewDocData.put("description", this.description);
        courseReviewDocData.put("ratedBy", db.collection("User").document(this.ratedByKey));
        courseReviewDocData.put("rating", this.rating);
        courseReviewDocData.put("ratedDateTime", new Timestamp(this.ratedDateTime));
        courseReviewDocData.put("course", db.collection("CourseModule").document(this.courseKey));
        return courseReviewDocData;
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


    public Date getRatedDateTime() {
        return ratedDateTime;
    }

    public void setRatedDateTime(Date ratedDateTime) {
        this.ratedDateTime = ratedDateTime;
    }

    public User getRatedBy() {
        return ratedBy;
    }

    public void setRatedBy(User ratedBy) {
        this.ratedBy = ratedBy;
    }


    public String getRatedByKey() {
        return ratedByKey;
    }

    public void setRatedByKey(String ratedByKey) {
        this.ratedByKey = ratedByKey;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getCourseKey() {
        return courseKey;
    }

    public void setCourseKey(String courseKey) {
        this.courseKey = courseKey;
    }
}
