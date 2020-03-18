package com.cz3002.sharetolearn.viewModel;

import android.util.Log;

import com.cz3002.sharetolearn.models.Chat;
import com.cz3002.sharetolearn.models.ChatMessage;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.model.Document;

import java.sql.Array;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ChatViewModel extends ViewModel {
    private MutableLiveData<ArrayList<ChatMessage>> mChatMessages = new MutableLiveData<>();
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private MutableLiveData<Chat> mChat = new MutableLiveData<>();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DocumentReference chatDoc;

    public ChatViewModel() {
        // get current login user id
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public LiveData<Chat> getChat(Course course) {
        realtimeChatData(course);
        return mChat;
    }

    private void realtimeChatData(final Course course) {
        chatDoc = db.collection("Chat").document();
        chatDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("Listen", "Listen failed.", e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) getFireStoreChatData(course);
                else getFireStoreChatData(course);
            }
        });
    }

    private void getFireStoreChatData(final Course course) {
        db.collection("Chat").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean chatExist = false;
                    chatMessages.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document != null) {
                            String key = document.getId();
                            String courseKey = document.getDocumentReference("course").getPath().substring(7);
                            Date dateCreated = document.getTimestamp("dateCreated").toDate();

                            Log.d("HEREE", courseKey);
                            Log.d("HEREE", course.getKey());
                            if (courseKey.equals(course.getKey())) {
                                chatExist = true;

                                // get list of messages
                                String b = String.valueOf(document.get("messages"));
                                if (b != "null" && b != null && b != "[]") {

                                    ArrayList<HashMap<String, String>> message = (ArrayList<HashMap<String, String>>) document.get("messages");
                                    for (HashMap<String, String> msgAttr : message) {
                                        String msg = msgAttr.get("message");
                                        String postedBy = msgAttr.get("postedBy");
                                        ChatMessage cm = new ChatMessage(msg, postedBy);
                                        chatMessages.add(cm);
                                    }
                                }
                                Chat c = new Chat(key, courseKey, dateCreated);
                                c.setChatMessages(chatMessages);
                                mChat.setValue(c);
                            }
                        }
                    }
                    if (chatExist == false) {
                        newChat(course);
                    }
                }
            }
        });
    }

    public void newChat(Course course) {
        final Chat newChat = new Chat("", course.getKey(), new Date());
        Map<String, Object> docData = newChat.getFireStoreFormat();
        db.collection("Chat")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Success", "DocumentSnapshot written with ID: " + documentReference.getId());
                        newChat.setKey(documentReference.getId());
                        mChat.setValue(newChat);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Failure", "Error adding document", e);
                    }
                });
    }

    public void newChatMessage(String message) {
        ChatMessage cm = new ChatMessage(message, "/User/" + currentUser.getUid());
        mChat.getValue().addChatMessages(cm);
        HashMap<String, Object> cmDoc = mChat.getValue().getFireStoreFormat();
        db.collection("Chat").document(mChat.getValue().getKey()).update(cmDoc);
    }
}
