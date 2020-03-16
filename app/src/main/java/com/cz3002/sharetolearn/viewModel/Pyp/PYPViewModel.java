package com.cz3002.sharetolearn.viewModel.Pyp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.cz3002.sharetolearn.models.PYP;
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

public class PYPViewModel extends ViewModel {
    private MutableLiveData<List<PYP>> mPyps;
    private List<PYP> PYPList = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference;

    public PYPViewModel() {
        mPyps = new MutableLiveData<>();
        realtimeFireStorePypData();
        getFireStorePypData();
    }

    public LiveData<List<PYP>> getpyps() {
        return mPyps;
    }

    public void getFireStorePypData() {
        db.collection("PYP").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    PYPList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            // get course details
                            String key = document.getId();
                            String courseKey = document.getDocumentReference("course").getId();
                            String title = document.getString("title");
                            String question = document.getString("question");
                            String postedByKey = document.getDocumentReference("postedBy").getId();
                            Date postedDateTime = document.getTimestamp("postedDateTime").toDate();
                            PYP pyp = new PYP(key, courseKey, question, postedByKey, title, postedDateTime);
                            for (DocumentReference response : (ArrayList<DocumentReference>) document.get("responses"))
                                pyp.addResponseKey(response.getId());
                            for (DocumentReference like : (ArrayList<DocumentReference>) document.get("likes"))
                                pyp.addLikeKey(like.getId());
                            LikeNumberPypLiveData.setLikeNumber(pyp);
                            CommentNumberPypLiveData.setCommentNumber(pyp);
                            PYPList.add(pyp);
                        }
                    }
                    mPyps.setValue(PYPList);
                }
            }
        });
    }

    public void realtimeFireStorePypData() {
        collectionReference = db.collection("PYP");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots,
                                @Nullable FirebaseFirestoreException e) {
                getFireStorePypData();
            }
        });
    }

    public static void addPYPFireStore(final Context context, PYP pyp) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> PYPDoc = pyp.getFireStoreFormat();
        db.collection("PYP")
                .add(PYPDoc)
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

    public static void removePYPFireStore(final Context context, PYP pyp){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> PYPDoc = pyp.getFireStoreFormat();
        final String docKey = pyp.getKey();
        db.collection("PYP")
                .document(docKey)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void task) {
                        Log.d("Success", "DocumentSnapshot deleted with ID: " + docKey);
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

    public static void updatePYP(final Context context, PYP pyp){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String,Object> PYPDoc = pyp.getFireStoreFormat();
        db.collection("PYP")
                .document(pyp.getKey())
                .update(PYPDoc);
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