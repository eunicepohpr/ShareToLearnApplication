package com.cz3002.sharetolearn.viewModel;

import com.cz3002.sharetolearn.models.Discussion;
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
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiscussionViewModel extends ViewModel {

    private MutableLiveData<List<Discussion>> mDiscussionThreads;
    private List<Discussion> discussionList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;

    public DiscussionViewModel() {
//        mDiscussionThreads = new MutableLiveData<>();
//        List<Discussion> discussionThreads = new ArrayList<>();
//
//        Discussion item1 = new Discussion("discussionKey1", "wJo35YneWvYNF5VNh3Mc", "Proof of Dijkstra algorithm",
//                "user1", "CZ2001 Help", new Date());
//        User user1 = new User();
//        user1.setKey("user1");
//        user1.setName("Lam");
//        item1.addResponseKey("response1");
//        item1.setPostedBy(user1);
//        LikeNumbersViewModel.setLikeNumber(item1.getKey(), item1.getLikeKeys().size());
//        discussionThreads.add(item1);
//
//        Discussion item2 = new Discussion("discussionKey2", "YxLTpfzIKMQOk4QifP8u", "How to solve TUT 3 question 1?",
//                "user2", "CZ1005 Tutorial", new Date());
//        User user2 = new User();
//        user2.setKey("user2");
//        user2.setName("John");
//        item2.addResponseKey("response2");
//        item2.setPostedBy(user2);
//        item2.addLikeKey("user1");
//        LikeNumbersViewModel.setLikeNumber(item2.getKey(), item2.getLikeKeys().size());
//        discussionThreads.add(item2);
//        mDiscussionThreads.setValue(discussionThreads);
        mDiscussionThreads = new MutableLiveData<>();
        realtimeFireStoreDiscussionData();
        getFireStoreDiscussionData();
    }

    public LiveData<List<Discussion>> getDiscussionThreads() {
        return mDiscussionThreads;
    }

    public void getFireStoreDiscussionData() {
        db.collection("Discussion").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                discussionList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    if (document != null) {
                        // get course details
                        String key = document.getId();
                        String courseKey = document.getDocumentReference("course").getId();
                        String title = document.getString("title");
                        String question = document.getString("question");
                        String postedByKey = document.getDocumentReference("postedBy").getId();
                        Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                        Discussion discussionThread = new Discussion(key, courseKey, question, postedByKey, title, postedDateTime);

                        for (DocumentReference like : (ArrayList<DocumentReference>) document.get("likes"))
                            discussionThread.addLikeKey(like.getId());
                        LikeNumbersLiveData.setLikeNumber(discussionThread);
                        CommentNumberLiveData.setCommentNumber(discussionThread);
                        discussionList.add(discussionThread);
                    }
                }
                mDiscussionThreads.setValue(discussionList);
            }
            }
        });
    }

    public void realtimeFireStoreDiscussionData() {
        collectionReference = db.collection("Discussion");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                getFireStoreDiscussionData();
            }
        });
    }
}