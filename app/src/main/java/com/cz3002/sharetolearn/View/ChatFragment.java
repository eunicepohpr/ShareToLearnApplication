package com.cz3002.sharetolearn.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.ChatMessage;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.viewModel.ChatViewModel;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private ChatViewModel chatViewModel;
    private Course course;
    private ImageButton IB;
    private EditText ET;

    public ChatFragment(Course course) {
        this.course = course;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        chatViewModel = ViewModelProviders.of(this).get(ChatViewModel.class);
        View root = inflater.inflate(R.layout.chat_fragment, container, false);

        IB = root.findViewById(R.id.chat_send);
        ET = root.findViewById(R.id.chat_ET);

        course.getKey();
        chatViewModel.getChat(course).observe(this, new Observer<ArrayList<ChatMessage>>() {
            @Override
            public void onChanged(ArrayList<ChatMessage> chatMsg) {
                chatMessages = chatMsg;
            }
        });

        IB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), ET.getText().toString(), Toast.LENGTH_SHORT);
            }
        });

        return root;
    }

}
