package com.cz3002.sharetolearn.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cz3002.sharetolearn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> mUser = new MutableLiveData<>();

    private ArrayList<User> userList = new ArrayList<>();
    private User currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ProfileViewModel(){

    }

    public LiveData<User> getUser(){
        getFirestoreUser();
        return mUser;
    }

    public LiveData<User> setUser(String bio){
        updateFirestoreUser(bio);
        return mUser;
    }

    public void getFirestoreUser(){
        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            if(document.getId().equals("u0V6npiHU87egeDnAZzG")){ //change to mAuth userId when done
                                String key = document.getId();
                                String name = document.getString("name");
                                String grad = document.getString("expectedYearOfGrad");
                                String course = document.getString("courseOfStudy");
                                String bio = document.getString("biography");
                                String email = document.getString("email");

                                User user = new User(key,bio,email,course,grad,name);

                                // get list of user likes
                                HashMap<String, Object> userLikes = (HashMap<String, Object>) document.get("likes");
                                if (userLikes.containsKey("pyp"))
                                    for (DocumentReference pypLike : (ArrayList<DocumentReference>) userLikes.get("pyp"))
                                        user.addPypLikeKey(pypLike.getPath().substring(4));
                                if (userLikes.containsKey("discussion"))
                                    for (DocumentReference discussionLike : (ArrayList<DocumentReference>) userLikes.get("discussion"))
                                        user.addDiscussionLikeKey(discussionLike.getPath().substring(10));

                                // get list of user ratings
                                HashMap<String, Object> userRatings = (HashMap<String, Object>) document.get("ratings");
                                if (userRatings.containsKey("pyp"))
                                    for (DocumentReference pypRating : (ArrayList<DocumentReference>) userRatings.get("pyp"))
                                        user.addPypRatingKey(pypRating.getPath().substring(11));
                                if (userRatings.containsKey("discussion"))
                                    for (DocumentReference discussionRating : (ArrayList<DocumentReference>) userRatings.get("discussion"))
                                        user.addDiscussionRatingKey(discussionRating.getPath().substring(18));

                                // get list of registered courses
                                String b = String.valueOf(document.get("registered"));
                                if (b != "null" && b != null && b != "[]") {
                                    for (DocumentReference registeredCourse : (ArrayList<DocumentReference>) document.get("registered")) {
                                        String courseRef = registeredCourse.getPath().substring(13);
                                        user.addRegisteredCourseKey(courseRef);
                                    }
                                }
                                currentUser = user;
                                mUser.setValue(user);
                            }
                        }
                    }
                }
            }
        });
    }

    public void updateFirestoreUser(String bio){
        currentUser.setBiography(bio);
        db.collection("User")
                .document(currentUser.getKey())
                .update("biography", bio);
        mUser.setValue(currentUser);
    }
}
