package com.cz3002.sharetolearn.viewModel;

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


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainUserViewModel extends ViewModel {
    private MutableLiveData<User> mMainUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;

    public MainUserViewModel(){
        mMainUser = new MutableLiveData<>();
        realtimeFireStoreMainUserData();
        getFirestoreUser();
    }

    public void getFirestoreUser() {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser fbCurrentUser = mAuth.getCurrentUser();
        if (fbCurrentUser != null) {
            db.collection("User").document(fbCurrentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        String key = documentSnapshot.getId();
                        String name = documentSnapshot.getString("name");
                        User mainUser = new User(key, name);
                        mMainUser.setValue(mainUser);
                    }
                }
            });
        }

    }

    public void realtimeFireStoreMainUserData() {
        mAuth = FirebaseAuth.getInstance();
        documentReference = db.collection("User").document(mAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                getFirestoreUser();
            }
        });
    }

    public LiveData<User> getMainUser() { return mMainUser; }

}
