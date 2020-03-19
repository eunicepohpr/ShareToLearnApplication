package com.cz3002.sharetolearn.viewModel;

import android.util.Log;

import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.User;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainUserViewModel extends ViewModel {

    private MutableLiveData<User> mMainUser = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Course>> mCourses = new MutableLiveData<>();
    private ArrayList<Course> courseList = new ArrayList<>();
    private User currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private DocumentReference courseDoc;

    public MainUserViewModel() {
        getFirestoreUser();
    }

    public void setMainUser(User user) {
        mMainUser = new MutableLiveData<>();
        mMainUser.setValue(user);
    }

    public LiveData<User> getUser() {
        return mMainUser;
    }

    public LiveData<ArrayList<Course>> getCourseList() {
        realtimeCourseData();
        return mCourses;
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

    public void getFirestoreUser() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbCurrentUser = mAuth.getCurrentUser();
        if (fbCurrentUser != null) {
            db.collection("User").document(fbCurrentUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot != null) {
                        String key = documentSnapshot.getId();
                        String name = documentSnapshot.getString("name");
                        String grad = documentSnapshot.getString("expectedYearOfGrad");
                        String course = documentSnapshot.getString("courseOfStudy");
                        String bio = documentSnapshot.getString("biography");
                        String email = documentSnapshot.getString("email");
                        String domain = documentSnapshot.getString("domain");
                        String imageUrl = documentSnapshot.getString("imageUrl");

                        User user = new User(key, bio, email, course, grad, name, domain, imageUrl);

                        // get list of user likes
                        HashMap<String, Object> userLikes = (HashMap<String, Object>) documentSnapshot.get("likes");
                        if (userLikes.containsKey("pyp"))
                            for (DocumentReference pypLike : (ArrayList<DocumentReference>) userLikes.get("pyp"))
                                user.addPypLikeKey(pypLike.getId());
                        if (userLikes.containsKey("discussion"))
                            for (DocumentReference discussionLike : (ArrayList<DocumentReference>) userLikes.get("discussion"))
                                user.addDiscussionLikeKey(discussionLike.getId());

                        // get list of user ratings
                        HashMap<String, Object> userRatings = (HashMap<String, Object>) documentSnapshot.get("ratings");
                        if (userRatings.containsKey("pyp"))
                            for (DocumentReference pypRating : (ArrayList<DocumentReference>) userRatings.get("pyp"))
                                user.addPypRatingKey(pypRating.getId());
                        if (userRatings.containsKey("discussion"))
                            for (DocumentReference discussionRating : (ArrayList<DocumentReference>) userRatings.get("discussion"))
                                user.addDiscussionRatingKey(discussionRating.getId());

                        // get list of registered courses
                        String b = String.valueOf(documentSnapshot.get("registered"));
                        if (b != "null" && b != null && b != "[]")
                            for (DocumentReference registeredCourse : (ArrayList<DocumentReference>) documentSnapshot.get("registered"))
                                user.addRegisteredCourseKey(registeredCourse.getId());

                        currentUser = user;
                        mMainUser.setValue(user);
                    }
                }
            });
        }
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
}
