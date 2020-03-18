package com.cz3002.sharetolearn.viewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cz3002.sharetolearn.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<User> mUser = new MutableLiveData<>();

    private User currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    public ProfileViewModel() {
    }

    public LiveData<User> getUser() {
        getFirestoreUser();
        return mUser;
    }

    public LiveData<User> setUser(String name, String cos, String eyos, String bio) {
        updateFirestoreUser(name, cos, eyos, bio);
        return mUser;
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
                        mUser.setValue(user);
                    }
                }
            });
        }
    }

    public void updateFirestoreUser(String name, String cos, String eyog, String bio) {
        currentUser.setName(name);
        currentUser.setCourseOfStudy(cos);
        currentUser.setExpectedYearOfGrad(eyog);
        currentUser.setBiography(bio);
        Map<String, Object> userDocData = new HashMap<>();
        userDocData.put("biography", currentUser.getBiography());
        userDocData.put("courseOfStudy", currentUser.getCourseOfStudy());
        userDocData.put("expectedYearOfGrad", currentUser.getExpectedYearOfGrad());
        userDocData.put("name", currentUser.getName());
        db.collection("User").document(currentUser.getKey()).update(userDocData);
        mUser.setValue(currentUser);
    }

    public void uploadImage(String mUri) {
        currentUser.setImageURL(mUri);
        Map<String, Object> userDocData = new HashMap<>();
        userDocData.put("imageUrl", currentUser.getImageURL());
        db.collection("User").document(currentUser.getKey()).update(userDocData);
    }
}
