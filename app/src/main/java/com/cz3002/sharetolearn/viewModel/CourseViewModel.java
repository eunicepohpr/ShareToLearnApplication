package com.cz3002.sharetolearn.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cz3002.sharetolearn.models.Course;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CourseViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Course>> mCourses = new MutableLiveData<>();
    private ArrayList<Course> courseList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private DocumentReference usersDoc;
    private FirebaseUser currentUser;

    public LiveData<ArrayList<Course>> getCourseList() {
        getFireStoreCoursesData();
        return mCourses;
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
}
