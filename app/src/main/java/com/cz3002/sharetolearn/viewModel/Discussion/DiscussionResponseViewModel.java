package com.cz3002.sharetolearn.viewModel.Discussion;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.DiscussionResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
        mDiscussionResponses.setValue(discussionResponses);
        realtimeFireStoreDiscussionData();
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
                        try {
                            if (document != null) {
                                // get course details
                                String key = document.getId();
                                String discussionKey = document.getDocumentReference("discussion").getId();
                                String postedByKey = document.getDocumentReference("postedBy").getId();
                                Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                                String answer = document.getString("answer");
                                DiscussionResponse response = new DiscussionResponse(key, discussionKey, postedByKey, answer, postedDateTime);
                                for (DocumentReference upvote : (ArrayList<DocumentReference>) document.get("upvotes"))
                                    response.addUpvoteKey(upvote.getId());
                                for (DocumentReference downvote : (ArrayList<DocumentReference>) document.get("downvotes"))
                                    response.addDownvoteKey(downvote.getId());
                                VoteNumberDiscussionLiveData.setVoteNumber(response);
                                discussionResponses.put(key, response);
                            }
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    mDiscussionResponses.setValue(discussionResponses);
                }
            }
        });
    }

    public void realtimeFireStoreDiscussionData() {
        collectionReference = db.collection("DiscussionResponse");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                getFireStoreDiscussionReponseData();
            }
        });
    }

    public static void addDiscussionResponse(final Context context, final Discussion discussion, DiscussionResponse response){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> responseDoc = response.getFireStoreFormat();
        db.collection("DiscussionResponse")
                .add(responseDoc)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                        discussion.addResponseKey(documentReference.getId());
                        db.collection("Discussion")
                                .document(discussion.getKey())
                                .update("responses", FieldValue.arrayUnion(documentReference));
                        Toast.makeText(context, "Successfully posted answer", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                        Toast.makeText(context, "Posted Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void removeDiscussionResponseFireStore(final Context context, DiscussionResponse response){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> doc = response.getFireStoreFormat();
        final String docKey = response.getKey();
        db.collection("DiscussionResponse")
                .document(docKey)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void task) {
                        Log.d("Success", "DocumentSnapshot deleted with ID: " + docKey);
                        Toast.makeText(context, "Successfully delete comment", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error deleting document", e);
                        Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void updateDiscussionResponseFireStore(final Context context, DiscussionResponse response){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> doc = response.getFireStoreFormat();
        db.collection("DiscussionResponse")
                .document(response.getKey())
                .update(doc);
    }
}
