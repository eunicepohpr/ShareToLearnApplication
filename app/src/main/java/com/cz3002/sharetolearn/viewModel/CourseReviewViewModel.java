package com.cz3002.sharetolearn.viewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;
import com.cz3002.sharetolearn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CourseReviewViewModel extends ViewModel {
    private MutableLiveData<User> mUser = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Course>> mCourses = new MutableLiveData<>();
    private MutableLiveData<ArrayList<CourseReview>> mReviewList = new MutableLiveData<>();
    private ArrayList<Course> courseList = new ArrayList<>();
    private ArrayList<CourseReview> reviewList = new ArrayList<>();
    private ArrayList<User> userList = new ArrayList<>();
//    private User currentUser;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private DocumentReference courseDoc;
    private DocumentReference reviewDoc;
    private FirebaseUser currentFbUser;

    public CourseReviewViewModel() {
        // get current login user
        mAuth = FirebaseAuth.getInstance();
        currentFbUser = mAuth.getCurrentUser();
    }

    public LiveData<ArrayList<CourseReview>> getCourseReviewList(Course selectedCourse) {
        getAllUserData();
        realtimeReviewData(selectedCourse);
        return mReviewList;
    }

    public LiveData<ArrayList<Course>> getCourseList() {
        realtimeCourseData();
        return mCourses;
    }

    public LiveData<User> getUser() {
        getAllUserData();
        return mUser;
    }

    public void realtimeCourseData() {
        courseDoc = db.collection("CourseModule").document();
        courseDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("Listen", "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    getFireStoreCoursesData();
                } else {
                    getFireStoreCoursesData();
                }
            }
        });
    }

    public void realtimeReviewData(final Course selectedCourse) {
        reviewDoc = db.collection("CourseModule").document(selectedCourse.getKey());
        reviewDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("Listen", "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    getFireStoreCourseReviewsData(selectedCourse);
                } else {
                    getFireStoreCourseReviewsData(selectedCourse);
                }
            }
        });
    }

    public void getAllUserData() {
        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    courseList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            String key = document.getId();
                            String name = document.getString("name");
                            String grad = document.getString("expectedYearOfGrad");
                            String course = document.getString("courseOfStudy");
                            String bio = document.getString("biography");
                            String email = document.getString("email");
                            String domain = document.getString("domain");
                            String imageUrl = document.getString("imageUrl");

                            User user = new User(key, bio, email, course, grad, name, domain, imageUrl);

                            // get list of user likes
                            HashMap<String, Object> userLikes = (HashMap<String, Object>) document.get("likes");
                            if (userLikes.containsKey("pyp"))
                                for (DocumentReference pypLike : (ArrayList<DocumentReference>) userLikes.get("pyp"))
                                    user.addPypLikeKey(pypLike.getId());
                            if (userLikes.containsKey("discussion"))
                                for (DocumentReference discussionLike : (ArrayList<DocumentReference>) userLikes.get("discussion"))
                                    user.addDiscussionLikeKey(discussionLike.getId());

                            // get list of user ratings
                            HashMap<String, Object> userRatings = (HashMap<String, Object>) document.get("ratings");
                            if (userRatings.containsKey("pyp"))
                                for (DocumentReference pypRating : (ArrayList<DocumentReference>) userRatings.get("pyp"))
                                    user.addPypRatingKey(pypRating.getId());
                            if (userRatings.containsKey("discussion"))
                                for (DocumentReference discussionRating : (ArrayList<DocumentReference>) userRatings.get("discussion"))
                                    user.addDiscussionRatingKey(discussionRating.getId());

                            // get list of registered courses
                            String b = String.valueOf(document.get("registered"));
                            if (b != "null" && b != null && b != "[]")
                                for (DocumentReference registeredCourse : (ArrayList<DocumentReference>) document.get("registered"))
                                    user.addRegisteredCourseKey(registeredCourse.getId());

                            userList.add(user);

                            if (user.getKey().equals(currentFbUser.getUid())) {
//                                currentUser = user;
                                mUser.setValue(user);
                            }
                        }
                    }
                }
            }
        });
    }

    public void getFireStoreCoursesData() {
        db.collection("CourseModule").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    courseList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            // get course details
                            String key = document.getId();
                            String courseCode = document.getString("courseCode");
                            String title = document.getString("title");
                            String description = document.getString("description");
                            String courseAssessment = document.getString("courseAssessment");

                            Course course = new Course(key, courseCode, title, description, courseAssessment);

                            // get list of registered users
                            String b = String.valueOf(document.get("registered"));
                            if (b != "null" && b != null && b != "[]") {
                                for (DocumentReference registeredUser : (ArrayList<DocumentReference>) document.get("registered")) {
                                    String userRef = registeredUser.getPath().substring(5);
                                    course.addRegisteredUserKeys(userRef);
                                }
                            }
                            // get list of reviews
                            String a = String.valueOf(document.get("reviews"));
                            if (a != "null" && a != null && a != "[]") {
                                for (DocumentReference reviews : (ArrayList<DocumentReference>) document.get("reviews")) {
                                    String reviewRef = reviews.getPath().substring(13);
                                    course.addReviewKeys(reviewRef);
                                }
                            }
                            courseList.add(course);
                        }
                    }
                    mCourses.setValue(courseList);
                }
            }
        });
    }

    public void getFireStoreCourseReviewsData(final Course selectedCourse) {
        db.collection("CourseReview").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    reviewList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            DocumentReference coursedoc = document.getDocumentReference("course");
                            if (selectedCourse.getKey().equals(coursedoc.getId())) {
                                String key = document.getId();
                                Double rating = document.getDouble("rating");
                                Timestamp ratedDateTime = document.getTimestamp("ratedDateTime");
                                Date dateTime = ratedDateTime.toDate();
                                DocumentReference ratedByKey = document.getDocumentReference("ratedBy");
                                String ratedByKeyString = ratedByKey.toString();
                                String description = document.getString("description");
                                String courseKey = ratedByKey.toString();
                                CourseReview courseReview = new CourseReview(key, rating, dateTime, ratedByKeyString, description, courseKey);

                                for (User u : userList) {
                                    String userKey = ratedByKey.getId();
                                    if (u.getKey().equals(userKey)) {
                                        courseReview.setRatedBy(u);
                                    }
                                }
                                reviewList.add(courseReview);
                            }
                        }
                    }
                    mReviewList.setValue(reviewList);
                }
            }
        });
    }

    public void newReview(CourseReview courseReview, final Course selectedCourse) {
        final Map<String, Object> docData = courseReview.getFireStoreReviewFormat();
        db.collection("CourseReview")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                        //add reference to CourseModule
                        selectedCourse.addReviewKeys(documentReference.getId());
                        Map<String, Object> docData = selectedCourse.getFireStoreFormat();
                        db.collection("CourseModule").document(selectedCourse.getKey()).set(docData);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                    }
                });
    }

    public void updateTime(CourseReview review) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("description", review.getDescription());
        docData.put("ratedBy", review.getRatedBy());
        //docData.put("ratedBy", db.collection("User").document("u0V6npiHU87egeDnAZzG"));
        docData.put("rating", review.getRating());
        docData.put("ratedDateTime", new Timestamp(new Date()));
        //docData.put("course", db.collection("CourseModule").document("YxLTpfzIKMQOk4QifP8u"));
/*        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();*/

        db.collection("CourseReview").document(review.getKey()).set(docData);
        //.update("ratedDateTime", getTimestamp());
    }
}
