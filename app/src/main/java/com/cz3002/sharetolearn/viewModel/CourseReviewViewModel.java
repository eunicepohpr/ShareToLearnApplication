package com.cz3002.sharetolearn.viewModel;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;
import com.google.android.gms.tasks.OnCompleteListener;
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

public class CourseReviewViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Course>> mCourses = new MutableLiveData<>();
    private MutableLiveData<ArrayList<CourseReview>> mReviewList = new MutableLiveData<>();

    private ArrayList<Course> courseList = new ArrayList<>();
    private ArrayList<CourseReview> reviewList = new ArrayList<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private DocumentReference usersDoc;
    private FirebaseUser currentUser;

    public CourseReviewViewModel(){
        // get current login user
        /*mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            usersDoc = db.collection("users").document(currentUser.getUid());
        }*/
        //usersDoc = db.collection("CourseReview").document();

/*        courseList = new ArrayList<String>();
        courseList.add("CZ2005 Operating System");
        courseList.add("CZ1011 Engineering Mathematics I");
        courseList.add("CZ3003 Software Design & Analysis");
        mCourses = new MutableLiveData<>();
        mCourses.setValue(courseList);*/
    }

/*    public LiveData<ArrayList<String>> getCourseList(){
        return mCourses;
    }*/

    public LiveData<ArrayList<CourseReview>> getCourseReviewList(Course selectedCourse) {
        getFireStoreCourseReviewsData(selectedCourse);
        return mReviewList;
    }

    public LiveData<ArrayList<Course>> getCourseList() {
        getFireStoreCoursesData();
        return mCourses;
    }

    public void realtimeFireStoreData() {
        usersDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("Listen", "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    //getFireStoreCourseReviewsData();
                } else {
                    //getFireStoreCourseReviewsData();
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
                            String a = String.valueOf(document.get("reviews"));
                            if (a != "null" || a != null || a != "[]"){
                                for (DocumentReference registeredUser : (ArrayList<DocumentReference>) document.get("reviews"))
                                    course.addRegisteredUserKeys(registeredUser.getPath());
                            }
                            // get list of registered users
                            String b = String.valueOf(document.get("registered"));
                            if (b != "null" || b != null || b != "[]"){
                                for (DocumentReference registeredUser : (ArrayList<DocumentReference>) document.get("registered"))
                                    course.addReviewKeys(registeredUser.getPath());
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
                            String k = selectedCourse.getKey();
                            String ck = coursedoc.getId();
                            if(selectedCourse.getKey().equals(coursedoc.getId())){
                                String key = document.getId();
                                Double rating = document.getDouble("rating");
                                Timestamp ratedDateTime = document.getTimestamp("ratedDateTime");
                                Date dateTime = ratedDateTime.toDate();
                                DocumentReference ratedByKey = document.getDocumentReference("ratedBy");
                                String ratedByKeyString = ratedByKey.toString();
                                String description = document.getString("description");
                                String courseKey = ratedByKey.toString();
                                CourseReview courseReview = new CourseReview(key, rating, dateTime, ratedByKeyString, description, courseKey);
                                reviewList.add(courseReview);
                            }
                        }
                    }
                    mReviewList.setValue(reviewList);
                }
            }
        });
    }

    public void updateTime(CourseReview review){
        Map<String, Object> docData = new HashMap<>();
        docData.put("description", review.getDescription());
        docData.put("ratedBy", review.getRatedBy());
        //docData.put("ratedBy", db.collection("User").document("u0V6npiHU87egeDnAZzG"));
        docData.put("rating", review.getRating());
        docData.put("ratedDateTime", new Timestamp(new Date()));
        //docData.put("course", db.collection("CourseModule").document("YxLTpfzIKMQOk4QifP8u"));
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        db.collection("CourseReview")
                .document(review.getKey())
                .set(docData);
                //.update("ratedDateTime", getTimestamp());
    }

    public String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",
                Locale.getDefault());
        return sdf.format(new Date());
    }
}
