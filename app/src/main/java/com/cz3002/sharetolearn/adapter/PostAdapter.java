package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.View.Discussion.DiscussionActivity;
import com.cz3002.sharetolearn.View.Pyp.PypActivity;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.PYP;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.Discussion.CommentNumberDiscussionLiveData;
import com.cz3002.sharetolearn.viewModel.Discussion.LikeNumbersDiscussionLiveData;
import com.cz3002.sharetolearn.viewModel.Pyp.CommentNumberPypLiveData;
import com.cz3002.sharetolearn.viewModel.Pyp.LikeNumberPypLiveData;
import com.cz3002.sharetolearn.viewModel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    public static final int DISCUSSION_TYPE = 0;
    public static final int PYP_TYPE = 1;
    Context context;
    FragmentActivity fragmentActivity;
    List<Object> posts;
    String mainUserKey;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);
    private UserViewModel userViewModel;

    public PostAdapter(Context context, FragmentActivity activity, List<Object> posts, String mainUserKey) {
        this.context = context;
        this.fragmentActivity = activity;
        this.posts = posts;
        this.mainUserKey = mainUserKey;
        this.userViewModel = ViewModelProviders.of(fragmentActivity).get(UserViewModel.class);
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType){
            case DISCUSSION_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_discussion_thread, parent, false);
                break;
            case PYP_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_pyp_thread, parent, false);
                break;
        }
        return new PostViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        View view = holder.view;
        final Object post = posts.get(position);
        if (post instanceof Discussion) {
            final Discussion discussionThread = (Discussion) post;

            TextView topicTextView = view.findViewById(R.id.topic_title);
            topicTextView.setText(discussionThread.getTitle());
            final TextView commentNumberTextView = view.findViewById(R.id.comment_number);
            CommentNumberDiscussionLiveData.getCommentNumber(discussionThread).observe(fragmentActivity, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer commentNumber) {
                    commentNumberTextView.setText(commentNumber.toString());
                }
            });
            final TextView likedTextView = view.findViewById(R.id.liked_number);
            LikeNumbersDiscussionLiveData.getLikeNumber(discussionThread).observe(fragmentActivity, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer likeNumber) {
                    likedTextView.setText(likeNumber.toString());
                }
            });
            final TextView postDetailsView = view.findViewById(R.id.postDetails);
            final ImageView userImage = view.findViewById(R.id.userImage);
            final View finalView = view;
            userViewModel.getUsers().observe(fragmentActivity, new Observer<HashMap<String, User>>() {
                @Override
                public void onChanged(HashMap<String, User> userMap) {
                    String imageUrl = "";
                    if (mainUserKey.equals(discussionThread.getPostedByKey())) {
                        postDetailsView.setText("Posted by you on " + dateFormat.format(discussionThread.getPostedDateTime()));
                        if (userMap.containsKey(mainUserKey))
                            imageUrl = userMap.get(mainUserKey).getImageURL();
                    } else {
                        String name = "...";
                        if (userMap.containsKey(discussionThread.getPostedByKey())) {
                            User user = userMap.get(discussionThread.getPostedByKey());
                            name = user.getName();
                            imageUrl = user.getImageURL();
                        }
                        postDetailsView.setText("Posted by " + name + " on " + dateFormat.format(discussionThread.getPostedDateTime()));
                    }
                    if (imageUrl != "")
                        Glide.with(finalView.getContext()).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(userImage);
                    else
                        Glide.with(finalView.getContext()).load(R.mipmap.ic_launcher_round).apply(RequestOptions.circleCropTransform()).into(userImage);
                }
            });
        } else if (post instanceof PYP) {
            final PYP pyp = (PYP) post;

            TextView topicTextView = view.findViewById(R.id.topic_title);
            topicTextView.setText(pyp.getTitle());
            final TextView commentNumberTextView = view.findViewById(R.id.comment_number);
            CommentNumberPypLiveData.getCommentNumber(pyp).observe(fragmentActivity, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer commentNumber) {
                    commentNumberTextView.setText(commentNumber.toString());
                }
            });
            final TextView likedTextView = view.findViewById(R.id.liked_number);
            LikeNumberPypLiveData.getLikeNumber(pyp).observe(fragmentActivity, new Observer<Integer>() {
                @Override
                public void onChanged(@Nullable Integer likeNumber) {
                    likedTextView.setText(likeNumber.toString());
                }
            });
            final TextView postDetailsView = view.findViewById(R.id.postDetails);
            final ImageView userImage = view.findViewById(R.id.userImage);
            final View finalView = view;
            userViewModel.getUsers().observe(fragmentActivity, new Observer<HashMap<String, User>>() {
                @Override
                public void onChanged(HashMap<String, User> userMap) {
                    String imageUrl = "";
                    if (mainUserKey.equals(pyp.getPostedByKey())) {
                        postDetailsView.setText("Posted by you on " + dateFormat.format(pyp.getPostedDateTime()));
                        if (userMap.containsKey(mainUserKey))
                            imageUrl = userMap.get(mainUserKey).getImageURL();
                    } else {
                        String name = "...";
                        if (userMap.containsKey(pyp.getPostedByKey())) {
                            User user = userMap.get(pyp.getPostedByKey());
                            name = user.getName();
                            imageUrl = user.getImageURL();
                        }
                        postDetailsView.setText("Posted by " + name + " on " + dateFormat.format(pyp.getPostedDateTime()));
                    }
                    if (imageUrl != "")
                        Glide.with(finalView.getContext()).load(imageUrl).apply(RequestOptions.circleCropTransform()).into(userImage);
                    else
                        Glide.with(finalView.getContext()).load(R.mipmap.ic_launcher_round).apply(RequestOptions.circleCropTransform()).into(userImage);
                }
            });
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (post instanceof Discussion) {
                    Discussion discussionThread = (Discussion) post;
                    Intent launchactivity = new Intent(context, DiscussionActivity.class);
                    launchactivity.putExtra("key", mainUserKey);
                    DiscussionActivity.discussionThread = discussionThread;
                    fragmentActivity.startActivity(launchactivity);
                } else {
                    PYP pyp = (PYP) post;
                    Intent launchactivity = new Intent(context, PypActivity.class);
                    launchactivity.putExtra("key", mainUserKey);
                    PypActivity.pyp = pyp;
                    fragmentActivity.startActivity(launchactivity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    public int getItemViewType(int position){
        if (posts.get(position) instanceof Discussion){
            return DISCUSSION_TYPE;
        } else return PYP_TYPE;
    }

    public void updateData(Context context, FragmentActivity activity, List<Object> posts, String mainUserKey) {
        this.context = context;
        this.fragmentActivity = activity;
        this.posts = posts;
        this.mainUserKey = mainUserKey;
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder{
        public View view;
        public PostViewHolder(View v){
            super(v);
            view = v;
        }
    }
}
