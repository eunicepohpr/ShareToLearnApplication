package com.cz3002.sharetolearn.viewModel.Pyp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cz3002.sharetolearn.models.PYP;
import com.cz3002.sharetolearn.models.PYPResponse;
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

public class PYPResponseViewModel extends ViewModel {
    private MutableLiveData<HashMap<String, PYPResponse>> mPYPResponses;
    private HashMap<String, PYPResponse> PYPResponses = new HashMap<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;

    public PYPResponseViewModel(){
        mPYPResponses = new MutableLiveData<>();
        mPYPResponses.setValue(PYPResponses);
        realtimeFireStorePYPData();
//        getFireStorePYPReponseData();
    }

    public LiveData<HashMap<String, PYPResponse>> getPYPResponse(){ return mPYPResponses; }

    public Map<String, PYPResponse> getPYPResponseMap(){ return PYPResponses; }

    public void getFireStorePYPReponseData() {
        db.collection("PYPResponse").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PYPResponses.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            // get course details
                            String key = document.getId();
                            String PYPKey = document.getDocumentReference("pyp").getId();
                            String postedByKey = document.getDocumentReference("postedBy").getId();
                            Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                            String answer = document.getString("answer");
                            PYPResponse response = new PYPResponse(key, postedByKey, PYPKey, answer, postedDateTime);
                            for (DocumentReference upvote : (ArrayList<DocumentReference>) document.get("upvotes"))
                                response.addUpvoteKey(upvote.getId());
                            for (DocumentReference downvote : (ArrayList<DocumentReference>) document.get("downvotes"))
                                response.addDownvoteKey(downvote.getId());
                            VoteNumberPypLiveData.setVoteNumber(response);
                            PYPResponses.put(key, response);
                        }
                    }
                    mPYPResponses.setValue(PYPResponses);
                }
            }
        });
    }

    public void realtimeFireStorePYPData() {
        collectionReference = db.collection("PYPResponse");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                getFireStorePYPReponseData();
            }
        });
    }

    public static void addPYPResponse(final Context context, final PYP pyp, PYPResponse response){
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> responseDoc = response.getFireStoreFormat();
        db.collection("PYPResponse")
                .add(responseDoc)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                        pyp.addResponseKey(documentReference.getId());
                        db.collection("PYP")
                                .document(pyp.getKey())
                                .update("responses", FieldValue.arrayUnion(documentReference));
                        Toast.makeText(context, "Successfully posted", Toast.LENGTH_SHORT).show();
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

    public static void removePYPResponseFireStore(final Context context, PYPResponse response){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> doc = response.getFireStoreFormat();
        final String docKey = response.getKey();
        db.collection("PYPResponse")
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

    public static void updatePYPResponseFireStore(final Context context, PYPResponse response){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> doc = response.getFireStoreFormat();
        db.collection("PYPResponse")
                .document(response.getKey())
                .update(doc);
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
