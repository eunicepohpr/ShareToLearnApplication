package com.cz3002.sharetolearn.viewModel.Discussion;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cz3002.sharetolearn.models.Discussion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import java.util.Map;

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
        mDiscussionThreads = new MutableLiveData<>();
        realtimeFireStoreDiscussionData();
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
                    try {
                        if (document != null) {
                            // get course details
                            String key = document.getId();
                            String courseKey = document.getDocumentReference("course").getId();
                            String title = document.getString("title");
                            String question = document.getString("question");
                            String postedByKey = document.getDocumentReference("postedBy").getId();
                            Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                            Discussion discussionThread = new Discussion(key, courseKey, question, postedByKey, title, postedDateTime);
                            for (DocumentReference response : (ArrayList<DocumentReference>) document.get("responses"))
                                discussionThread.addResponseKey(response.getId());
                            for (DocumentReference like : (ArrayList<DocumentReference>) document.get("likes"))
                                discussionThread.addLikeKey(like.getId());
                            LikeNumbersDiscussionLiveData.setLikeNumber(discussionThread);
                            CommentNumberDiscussionLiveData.setCommentNumber(discussionThread);
                            discussionList.add(discussionThread);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
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

    public static void addDiscussionFireStore(final Context context, Discussion discussionThread) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> discussionDoc = discussionThread.getFireStoreFormat();
        db.collection("Discussion")
                .add(discussionDoc)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                        Toast.makeText(context, "Successfully posted", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                        Toast.makeText(context, "post failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void removeDiscussionFireStore(final Context context, final Discussion discussionThread){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> discussionDoc = discussionThread.getFireStoreFormat();
        final String docKey = discussionThread.getKey();

        db.collection("Discussion")
                .document(docKey)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void task) {
                        Log.d("Success", "DocumentSnapshot deleted with ID: " + docKey);
                        for (DocumentReference doc: discussionThread.getReferenceListFireStoreFormat(discussionThread.getResponseKeys(), "DiscussionResponses"))
                            doc.delete();
                        Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
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

    public static void updateDiscussion(final Context context, Discussion discussionThread){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> discussionDoc = discussionThread.getFireStoreFormat();
        db.collection("Discussion")
                .document(discussionThread.getKey())
                .update(discussionDoc);
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
//                    }
//                });

    }
}