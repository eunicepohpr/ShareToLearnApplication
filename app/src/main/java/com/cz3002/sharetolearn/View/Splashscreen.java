package com.cz3002.sharetolearn.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.SQLite.ShareToLearn;
import com.cz3002.sharetolearn.SQLite.ShareToLearnDbHelper;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.DiscussionResponse;
import com.cz3002.sharetolearn.models.PYP;
import com.cz3002.sharetolearn.models.PYPResponse;
import com.cz3002.sharetolearn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class Splashscreen extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progress;

    private HashMap<String, Course> courses = new HashMap<>();
    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, Discussion> discussions = new HashMap<>();
    private HashMap<String, PYP> pyps = new HashMap<>();
    private HashMap<String, CourseReview> courseReviews = new HashMap<>();
    private HashMap<String, DiscussionResponse> discussionResponses = new HashMap<>();
    private HashMap<String, PYPResponse> pypResponses = new HashMap<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private ShareToLearnDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        progressBar = findViewById(R.id.pBar);

        getFireStoreCoursesData();
        getFireStoreUsersData();
        getFireStoreDiscussionsData();
        getFireStorePYPsData();
        getFireStoreCourseReviewsData();
        getFireStoreDiscussionResponsesData();
        getFireStorePYPResponsesData();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    for (progress = 10; progress < 100; progress = progress + 10) {
                        sleep(200); //run for 2 secs then sleep
                        progressBar.setProgress(progress);
                    }
                    Intent intent = new Intent(getApplicationContext(), SignIn.class);
                    //to direct it to this activity after the splash screen finishes
                    startActivity(intent);
                    finish(); //to stop it from rerunning

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();//to start the method
    }

    public void getFireStoreCoursesData() {
        db.collection("CourseModule").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    courses.clear();
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
                            if (a != "null" || a != null || a != "[]")
                                for (DocumentReference registeredUser : (ArrayList<DocumentReference>) document.get("reviews"))
                                    course.addRegisteredUserKeys(registeredUser);

                            // get list of registered users
                            String b = String.valueOf(document.get("registered"));
                            if (b != "null" || b != null || b != "[]")
                                for (DocumentReference registeredUser : (ArrayList<DocumentReference>) document.get("registered"))
                                    course.addReviewKeys(registeredUser);

                            courses.put(key, course); // add to hashmap
                        }
                    }
                }
            }
        });
    }

    public void getFireStoreUsersData() {
        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    users.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            // get user details
                            String key = document.getId();
                            String name = document.getString("name");
                            String bio = document.getString("bio");
                            String courseOfStudy = document.getString("courseOfStudy");
                            String email = document.getString("email");
                            String expectedYearOfGrad = document.getString("expectedYearOfGrad");

                            User user = new User(key, bio, email, courseOfStudy, expectedYearOfGrad, name);

                            // get list of user registered courses
                            String b = String.valueOf(document.get("registered"));
                            if (b != "null" || b != null || b != "[]")
                                for (DocumentReference registeredCourse : (ArrayList<DocumentReference>) document.get("registered"))
                                    user.addRegisteredCourseKey(registeredCourse);

                            // get list of user likes
                            HashMap<String, Object> userLikes = (HashMap<String, Object>) document.get("likes");
                            ArrayList<DocumentReference> pypLikes = userLikes.containsKey("pyp") ? (ArrayList<DocumentReference>) userLikes.get("pyp") : new ArrayList<DocumentReference>();
                            ArrayList<DocumentReference> discussionLikes = userLikes.containsKey("discussion") ? (ArrayList<DocumentReference>) userLikes.get("discussion") : new ArrayList<DocumentReference>();

                            user.setPypLikeKeys(pypLikes);
                            user.setDiscussionLikeKeys(discussionLikes);

                            // get list of user likes
                            HashMap<String, Object> userRatings = (HashMap<String, Object>) document.get("ratings");
                            ArrayList<DocumentReference> pypRatings = userRatings.containsKey("pyp") ? (ArrayList<DocumentReference>) userLikes.get("pyp") : new ArrayList<DocumentReference>();
                            ArrayList<DocumentReference> discussionRatings = userRatings.containsKey("discussion") ? (ArrayList<DocumentReference>) userLikes.get("discussion") : new ArrayList<DocumentReference>();

                            user.setPypRatingKeys(pypRatings);
                            user.setDiscussionRatingKeys(discussionRatings);

                            users.put(key, user); // add to hashmap
                        }
                    }
                }
            }
        });
    }

    public void getFireStoreDiscussionsData() {
        db.collection("Discussion").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    courses.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            String key = document.getId();
                            String title = document.getString("title");
                            String question = document.getString("question");
                            Timestamp postedDateTime = document.getTimestamp("postedDateTime");
                            DocumentReference courseKey = document.getDocumentReference("course");
                            DocumentReference postedByKey = document.getDocumentReference("postedBy");

                            Discussion discussion = new Discussion(key, courseKey, question, postedByKey, title, postedDateTime);

                            // get list of responses
                            String a = String.valueOf(document.get("responses"));
                            if (a != "null" || a != null || a != "[]")
                                for (DocumentReference response : (ArrayList<DocumentReference>) document.get("responses"))
                                    discussion.addResponseKey(response);

                            // get list of user likes
                            String b = String.valueOf(document.get("likes"));
                            if (b != "null" || b != null || b != "[]")
                                for (DocumentReference userLike : (ArrayList<DocumentReference>) document.get("likes"))
                                    discussion.addLikeKey(userLike);

                            discussions.put(key, discussion); //add to hashmap
                        }
                    }
                }
            }
        });
    }

    public void getFireStorePYPsData() {
        db.collection("PYP").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    courses.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            String key = document.getId();
                            String title = document.getString("title");
                            String question = document.getString("question");
                            Timestamp postedDateTime = document.getTimestamp("postedDateTime");
                            DocumentReference courseKey = document.getDocumentReference("course");
                            DocumentReference postedByKey = document.getDocumentReference("postedBy");

                            PYP pyp = new PYP(key, courseKey, postedByKey, question, title, postedDateTime);

                            // get list of responses
                            String a = String.valueOf(document.get("responses"));
                            if (a != "null" || a != null || a != "[]")
                                for (DocumentReference response : (ArrayList<DocumentReference>) document.get("responses"))
                                    pyp.addResponseKey(response);

                            // get list of user likes
                            String b = String.valueOf(document.get("likes"));
                            if (b != "null" || b != null || b != "[]")
                                for (DocumentReference userLike : (ArrayList<DocumentReference>) document.get("likes"))
                                    pyp.addLikeKey(userLike);

                            pyps.put(key, pyp); // add to hashmap
                        }
                    }
                }
            }
        });
    }

    public void getFireStoreCourseReviewsData() {
        db.collection("CourseReview").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    courses.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            String key = document.getId();
                            Double rating = document.getDouble("rating");
                            Timestamp ratedDateTime = document.getTimestamp("ratedDateTime");
                            DocumentReference ratedByKey = document.getDocumentReference("ratedBy");

                            CourseReview courseReview = new CourseReview(key, rating, ratedDateTime, ratedByKey);

                            courseReviews.put(key, courseReview); // add to hashmap
                        }
                    }
                }
            }
        });
    }

    public void getFireStoreDiscussionResponsesData() {
        db.collection("DiscussionResponse").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    courses.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            String key = document.getId();
                            String answer = document.getString("answer");
                            Timestamp postedDateTime = document.getTimestamp("postedDateTime");
                            DocumentReference discussionKey = document.getDocumentReference("discussion");
                            DocumentReference postedByKey = document.getDocumentReference("postedBy");

                            DiscussionResponse discussionResponse = new DiscussionResponse(key, discussionKey, postedByKey, answer, postedDateTime);

                            // get list of upvotes
                            String a = String.valueOf(document.get("upvotes"));
                            if (a != "null" || a != null || a != "[]")
                                for (DocumentReference upvote : (ArrayList<DocumentReference>) document.get("upvotes"))
                                    discussionResponse.addUpvoteKey(upvote);

                            // get list of downvotes
                            String b = String.valueOf(document.get("downvotes"));
                            if (b != "null" || b != null || b != "[]")
                                for (DocumentReference downvote : (ArrayList<DocumentReference>) document.get("downvotes"))
                                    discussionResponse.addDownvoteKey(downvote);

                            discussionResponses.put(key, discussionResponse);
                        }
                    }
                }
            }
        });
    }

    public void getFireStorePYPResponsesData() {
        db.collection("PYPResponse").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    courses.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            String key = document.getId();
                            String answer = document.getString("answer");
                            String working = document.getString("working");
                            Timestamp postedDateTime = document.getTimestamp("postedDateTime");
                            DocumentReference pypKey = document.getDocumentReference("pyp");
                            DocumentReference postedByKey = document.getDocumentReference("postedBy");

                            PYPResponse pypResponse = new PYPResponse(key, postedByKey, pypKey, working, answer, postedDateTime);

                            // get list of upvotes
                            String a = String.valueOf(document.get("upvotes"));
                            if (a != "null" || a != null || a != "[]")
                                for (DocumentReference upvote : (ArrayList<DocumentReference>) document.get("upvotes"))
                                    pypResponse.addUpvoteKey(upvote);

                            // get list of downvotes
                            String b = String.valueOf(document.get("downvotes"));
                            if (b != "null" || b != null || b != "[]")
                                for (DocumentReference downvote : (ArrayList<DocumentReference>) document.get("downvotes"))
                                    pypResponse.addDownvoteKey(downvote);

                            pypResponses.put(key, pypResponse);
                        }
                    }
                }
            }
        });
    }

//    public void addToSQLite(String TABLE_NAME, String COLUMN_NAME, HashMap<String, Course> obj) {
//        dbHelper = new ShareToLearnDbHelper(getApplicationContext());
//
//        try {
//            SQLiteDatabase db = dbHelper.getWritableDatabase();
//            ContentValues values = new ContentValues();
//            GsonBuilder gsonMapBuilder = new GsonBuilder();
//
//            Gson gson = gsonMapBuilder.create();
//            Type type = new TypeToken<HashMap<String, Course>>(){}.getType();
//            String json = gson.toJson(obj, type);
//            Log.d("ADDING TO DB", json);
//            values.put(COLUMN_NAME, json); //add question to table
//            db.insertOrThrow(TABLE_NAME, null, values);
//
//        } catch (Exception e) {
//            Log.e("ERROR at addToSQLite", e.toString());
//        } finally {
//            dbHelper.close();
//        }
//
//    }
//
//    public HashMap<String, Object> getFromSQLite(String TABLE_NAME, String COLUMN_NAME) {
//        String[] FROM = {COLUMN_NAME};
//        dbHelper = new ShareToLearnDbHelper(getApplicationContext());
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        try {
//            SQLiteDatabase db = dbHelper.getReadableDatabase();
//            Cursor cursor = db.query(TABLE_NAME, FROM, null, null, null, null, null);
//            Log.d("SQLiteDB", cursor.toString());
//            while (cursor.moveToNext()) {
//                for (int j = 0; j < cursor.getCount(); j++) {
//                    String content = cursor.getString(0);
//                    Log.d("SQLiteDB: ", content);
//                    Gson gson = new Gson();
//                    map = (HashMap<String, Object>) gson.fromJson(content, map.getClass());
//                }
//            }
//            return map;
//        } finally {
//            dbHelper.close();
//        }
//    }
}
