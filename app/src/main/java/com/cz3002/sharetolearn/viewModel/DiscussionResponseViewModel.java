package com.cz3002.sharetolearn.viewModel;

import com.cz3002.sharetolearn.models.DiscussionResponse;
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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiscussionResponseViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, DiscussionResponse>> mDiscussionResponses;
    private HashMap<String, DiscussionResponse> discussionResponses = new HashMap<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;

    public DiscussionResponseViewModel(){
        mDiscussionResponses = new MutableLiveData<>();
        realtimeFireStoreDiscussionData();
        getFireStoreDiscussionReponseData();
//        HashSet<DiscussionResponse> discussionResponses = new HashSet<>();
//
//        DiscussionResponse discussionResponse1 = new DiscussionResponse("response1", "discussionKey1", "someUser1",
//                "I dont know", new Date());
//        User responseUser1 = new User();
//        responseUser1.setKey("someUser1");
//        responseUser1.setName("Jess");
//        discussionResponse1.setPostedBy(responseUser1);
//        discussionResponses.add(discussionResponse1);
//
//        DiscussionResponse discussionResponse2 = new DiscussionResponse("response2", "discussionKey1", "someUser2",
//                "please google it", new Date());
//        User responseUser2 = new User();
//        responseUser2.setKey("someUser2");
//        responseUser2.setName("Sean");
//        discussionResponse2.setPostedBy(responseUser2);
//        discussionResponses.add(discussionResponse2);
//
//        mDiscussionResponses.setValue(discussionResponses);

    }

    public LiveData<HashMap<String, DiscussionResponse>> getDiscussionResponse(){ return mDiscussionResponses; }

    public Map<String, DiscussionResponse> getDiscussionResponseMap(){ return discussionResponses; }

    public void getFireStoreDiscussionReponseData() {
        db.collection("DiscussionResponse").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    discussionResponses.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            // get course details
                            String key = document.getId();
                            String discussionKey = document.getDocumentReference("discussion").getId();
                            String question = document.getString("question");
                            String postedByKey = document.getDocumentReference("postedBy").getId();
                            Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                            String answer = document.getString("answer");
                            DiscussionResponse response = new DiscussionResponse(key, discussionKey, postedByKey, answer, postedDateTime);
                            for (DocumentReference upvote : (ArrayList<DocumentReference>) document.get("upvotes"))
                                response.addUpvoteKey(upvote.getId());
                            for (DocumentReference downvote : (ArrayList<DocumentReference>) document.get("downvotes"))
                                response.addDownvoteKey(downvote.getId());
                            discussionResponses.put(key, response);
                        }
                    }
                    mDiscussionResponses.setValue(discussionResponses);
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
                getFireStoreDiscussionReponseData();
            }
        });
    }
}
