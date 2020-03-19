package com.cz3002.sharetolearn.viewModel;

import com.cz3002.sharetolearn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, User>> mUsers;
    private HashMap<String, User> userMap = new HashMap<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;

    public UserViewModel() {
        mUsers = new MutableLiveData<>();
        mUsers.setValue(userMap);
        realtimeFireStoreUserData();
    }

    public HashMap<String, User> getUserMap(){
        return userMap;
    }

    public LiveData<HashMap<String, User>> getUsers() {
        return mUsers;
    }

    public void getFireStoreUserData() {
        db.collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    userMap.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            // get course details
                            String key = document.getId();
                            String name = document.getString("name");
                            String imageUrl = document.getString("imageUrl");
                            User user = new User(key, name, imageUrl);
                            userMap.put(key, user);
                        }
                    }
                    mUsers.setValue(userMap);
                }
            }
        });
    }

    public void realtimeFireStoreUserData() {
        collectionReference = db.collection("User");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                getFireStoreUserData();
            }
        });
    }
}
