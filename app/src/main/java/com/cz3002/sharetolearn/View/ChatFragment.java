package com.cz3002.sharetolearn.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.ChatAdapter;
import com.cz3002.sharetolearn.models.Chat;
import com.cz3002.sharetolearn.models.ChatMessage;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.viewModel.ChatViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private Chat chat;
    private ChatViewModel chatViewModel;
    private Course course;
    private String mainUserKey;
    private ImageButton IB;
    private EditText ET;
    private RecyclerView messageView;
    private ChatAdapter chatAdapter;
    private boolean scrollBack = false;

    public ChatFragment(Course course) {
        this.course = course;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        View root = inflater.inflate(R.layout.chat_fragment, container, false);
        mainUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        IB = root.findViewById(R.id.chat_send);
        ET = root.findViewById(R.id.chat_ET);
        messageView = root.findViewById(R.id.messages_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(false);
        linearLayoutManager.setStackFromEnd(true);
        messageView.setLayoutManager(linearLayoutManager);
        chatAdapter = new ChatAdapter(getActivity(), new ArrayList<ChatMessage>(), mainUserKey);
        messageView.setAdapter(chatAdapter);
        chatViewModel.getChat(course).observe(this, new Observer<Chat>() {
            @Override
            public void onChanged(Chat chatMsg) {
                if (chatMsg == null || !chatMsg.getCourseKey().equals(course.getKey())) return;
                chat = chatMsg;
                chatAdapter.updateData(chat.getChatMessages(), mainUserKey);
                if (scrollBack){
                    messageView.scrollToPosition(chatAdapter.getItemCount()-1);
                    scrollBack = false;
                }
            }
        });
        messageView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int left, int top, int right, int bottom,
                                                int oldLeft, int oldTop, int oldRight, int oldBottom) {
                messageView.scrollToPosition(chatAdapter.getItemCount()-1);
            }
        });

        IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(), ET.getText().toString(), Toast.LENGTH_SHORT);
//                chatViewModel.newChatMessage(ET.getText().toString());
                // add new bubble
                if (chat == null || !chat.getCourseKey().equals(course.getKey())) return;
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                ChatMessage cm = new ChatMessage(ET.getText().toString(), mainUserKey);
                if (!chat.getChatMessages().contains(cm))
                    db.collection("Chat")
                        .document(chat.getKey())
                        .update("messages", FieldValue.arrayUnion(cm.hashFormat()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.w("Success", "Done");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Failure", "Error adding document", e);
                            }
                        });
                else chatViewModel.newChatMessage(ET.getText().toString());
                ET.setText("");// clear the input after adding
                scrollBack = true;
            }
        });

        return root;
    }

}
