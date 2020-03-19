package com.cz3002.sharetolearn.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.ChatMessage;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.UserViewModel;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    public static final int MY_MESSAGE = 0;
    public static final int THEIR_MESSAGE = 1;

    private FragmentActivity activity;
    private UserViewModel userViewModel;
    private List<ChatMessage> messages;
    private String mainUserKey;

    public ChatAdapter(FragmentActivity activity, List<ChatMessage> messages, String mainUserKey) {
        this.activity = activity;
        userViewModel = ViewModelProviders.of(activity).get(UserViewModel.class);
        this.messages = messages;
        this.mainUserKey = mainUserKey;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MY_MESSAGE)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_message, parent, false);
        else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.their_message, parent, false);

        return new ChatViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getPostedByKey().equals(mainUserKey)) {
            return MY_MESSAGE;
        } else return THEIR_MESSAGE;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChatViewHolder holder, int position) {
        final ChatMessage message = messages.get(position);
        if (message.getPostedByKey().equals(mainUserKey)) {
            TextView myMessage = holder.view.findViewById(R.id.my_message);
            myMessage.setText(message.getMessage());
        } else {
            final TextView theirName = holder.view.findViewById(R.id.their_name);
            TextView theirMessage = holder.view.findViewById(R.id.their_message);
            final ImageView avatar = holder.view.findViewById(R.id.avatar);
            theirMessage.setText(message.getMessage());
            userViewModel.getUsers().observe(activity, new Observer<HashMap<String, User>>() {
                @Override
                public void onChanged(HashMap<String, User> userMap) {
                    String imageUrl = "";
                    if (userMap == null || !userMap.containsKey(message.getPostedByKey())) return;
                    theirName.setText(userMap.get(message.getPostedByKey()).getName());
                    imageUrl = userMap.get(message.getPostedByKey()).getImageURL();
                    if (imageUrl != "")
                        Glide.with(holder.view.getContext()).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(avatar);
                    else
                        Glide.with(holder.view.getContext()).load(R.mipmap.ic_launcher_round).apply(RequestOptions.circleCropTransform()).into(avatar);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ChatViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    public void updateData(List<ChatMessage> messages, String mainUserKey) {
        userViewModel = ViewModelProviders.of(activity).get(UserViewModel.class);
        this.messages = messages;
        this.mainUserKey = mainUserKey;
        notifyDataSetChanged();
    }
}
