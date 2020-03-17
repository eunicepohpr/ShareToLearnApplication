package com.cz3002.sharetolearn.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.DiscussionResponse;
import com.cz3002.sharetolearn.models.PYP;
import com.cz3002.sharetolearn.models.PYPResponse;
import com.cz3002.sharetolearn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

//        getFireStoreCoursesData();
//        getFireStoreUsersData();
//        getFireStoreDiscussionsData();
//        getFireStorePYPsData();
//        getFireStoreCourseReviewsData();
//        getFireStoreDiscussionResponsesData();
//        getFireStorePYPResponsesData();

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    for (progress = 10; progress < 100; progress = progress + 10) {
                        sleep(200); //run for 2 secs then sleep
                        progressBar.setProgress(progress);
                    }
                    Intent intent = new Intent(getApplicationContext(), SignIn.class);
                    Bundle args = new Bundle();
//                    args.putSerializable("ShareToLearnApp", shareToLearnApp);
                    intent.putExtra("BUNDLE", args);
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
                            if (a != "null" && a != null && a != "[]")
                                for (DocumentReference registeredUser : (ArrayList<DocumentReference>) document.get("reviews"))
                                    course.addRegisteredUserKeys(registeredUser.getId());

                            // get list of registered users
                            String b = String.valueOf(document.get("registered"));
                            if (b != "null" && b != null && b != "[]")
                                for (DocumentReference registeredUser : (ArrayList<DocumentReference>) document.get("registered"))
                                    course.addReviewKeys(registeredUser.getId());

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
                            String domain = document.getString("domain");
                            String imageUrl = document.getString("imageUrl");

                            User user = new User(key, bio, email, courseOfStudy, expectedYearOfGrad, name, domain, imageUrl);

                            // get list of user registered courses
                            String b = String.valueOf(document.get("registered"));
                            if (b != "null" && b != null && b != "[]")
                                for (DocumentReference registeredCourse : (ArrayList<DocumentReference>) document.get("registered"))
                                    user.addRegisteredCourseKey(registeredCourse.getId());

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
                            Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                            DocumentReference courseKey = document.getDocumentReference("course");
                            DocumentReference postedByKey = document.getDocumentReference("postedBy");

                            Discussion discussion = new Discussion(key, courseKey.getId(), question,
                                    postedByKey.getId(), title, postedDateTime);

                            // get list of responses
                            String a = String.valueOf(document.get("responses"));
                            if (a != "null" && a != null && a != "[]")
                                for (DocumentReference response : (ArrayList<DocumentReference>) document.get("responses"))
                                    discussion.addResponseKey(response.getId());

                            // get list of user likes
                            String b = String.valueOf(document.get("likes"));
                            if (b != "null" && b != null && b != "[]")
                                for (DocumentReference userLike : (ArrayList<DocumentReference>) document.get("likes"))
                                    discussion.addLikeKey(userLike.getId());

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
                            Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                            DocumentReference courseKey = document.getDocumentReference("course");
                            DocumentReference postedByKey = document.getDocumentReference("postedBy");

                            PYP pyp = new PYP(key, courseKey.getId(), postedByKey.getId(),
                                    question, title, postedDateTime);

                            // get list of responses
                            String a = String.valueOf(document.get("responses"));
                            if (a != "null" && a != null && a != "[]")
                                for (DocumentReference response : (ArrayList<DocumentReference>) document.get("responses"))
                                    pyp.addResponseKey(response.getId());

                            // get list of user likes
                            String b = String.valueOf(document.get("likes"));
                            if (b != "null" && b != null && b != "[]")
                                for (DocumentReference userLike : (ArrayList<DocumentReference>) document.get("likes"))
                                    pyp.addLikeKey(userLike.getId());

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
                            Date ratedDateTime = document.getTimestamp("ratedDateTime").toDate();
                            DocumentReference ratedByKey = document.getDocumentReference("ratedBy");
                            String description = document.getString("description");
                            DocumentReference courseKey = document.getDocumentReference("course");

                            CourseReview courseReview = new CourseReview(key, rating, ratedDateTime,
                                    description, courseKey.getId(), ratedByKey.getId());

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
                            Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                            DocumentReference discussionKey = document.getDocumentReference("discussion");
                            DocumentReference postedByKey = document.getDocumentReference("postedBy");

                            DiscussionResponse discussionResponse = new DiscussionResponse(key,
                                    discussionKey.getId(), postedByKey.getId(), answer, postedDateTime);

                            // get list of upvotes
                            String a = String.valueOf(document.get("upvotes"));
                            if (a != "null" && a != null && a != "[]")
                                for (DocumentReference upvote : (ArrayList<DocumentReference>) document.get("upvotes"))
                                    discussionResponse.addUpvoteKey(upvote.getId());

                            // get list of downvotes
                            String b = String.valueOf(document.get("downvotes"));
                            if (b != "null" && b != null && b != "[]")
                                for (DocumentReference downvote : (ArrayList<DocumentReference>) document.get("downvotes"))
                                    discussionResponse.addDownvoteKey(downvote.getId());

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
                            Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                            DocumentReference pypKey = document.getDocumentReference("pyp");
                            DocumentReference postedByKey = document.getDocumentReference("postedBy");

                            PYPResponse pypResponse = new PYPResponse(key, postedByKey.getId(),
                                    pypKey.getId(), answer, postedDateTime);

                            // get list of upvotes
                            String a = String.valueOf(document.get("upvotes"));
                            if (a != "null" && a != null && a != "[]")
                                for (DocumentReference upvote : (ArrayList<DocumentReference>) document.get("upvotes"))
                                    pypResponse.addUpvoteKey(upvote.getId());

                            // get list of downvotes
                            String b = String.valueOf(document.get("downvotes"));
                            if (b != "null" && b != null && b != "[]")
                                for (DocumentReference downvote : (ArrayList<DocumentReference>) document.get("downvotes"))
                                    pypResponse.addDownvoteKey(downvote.getId());

                            pypResponses.put(key, pypResponse);
                        }
                    }
                }
            }
        });
    }

    public void addDiscussionFireStore(Discussion discussion) {
        Map<String, Object> disDocData = discussion.getFireStoreFormat();

        db.collection("Discussion")
                .add(disDocData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                    }
                });
    }

    public void addDiscussionResponseFireStore(DiscussionResponse discussionResponse) {
        Map<String, Object> docData = discussionResponse.getFireStoreFormat();

        db.collection("DiscussionResponse")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                    }
                });
    }

    public void addPYPFireStore(PYP pyp) {
        Map<String, Object> pypDocData = pyp.getFireStoreFormat();
        db.collection("PYP")
                .add(pypDocData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                    }
                });
    }

    public void addPYPResponseFireStore(PYPResponse pypResponse) {
        Map<String, Object> docData = pypResponse.getFireStoreFormat();
        db.collection("PYPResponse")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                    }
                });
    }

    public void addUserFireStore(User user) {
        Map<String, Object> userDocData = user.getFireStoreFormat();
        db.collection("User")
                .add(userDocData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                    }
                });
    }

    public void registerCourseFireStore(Course course, User user) {
        Map<String, Object> userDocData = user.getFireStoreFormat();
        db.collection("User").document(user.getKey()).update(userDocData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Success", "User updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error updating user document", e);
                    }
                });

        Map<String, Object> courseDocData = course.getFireStoreFormat();
        db.collection("Course").document(course.getKey()).update(courseDocData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Success", "Course updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error updating course document", e);
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
