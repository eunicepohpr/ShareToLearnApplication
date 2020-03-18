package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;
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

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PostAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater = null;
    private FragmentActivity fragmentActivity;
    private List<Object> posts;
    private String mainUserKey;
    private UserViewModel userViewModel;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);

    public PostAdapter(Context context, FragmentActivity activity, List<Object> posts, String mainUserKey){
        this.context = context;
        this.fragmentActivity = activity;
        this.posts = posts;
        this.mainUserKey = mainUserKey;
        this.userViewModel = ViewModelProviders.of(fragmentActivity).get(UserViewModel.class);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Object post = posts.get(i);
        if (post instanceof Discussion){
            final Discussion discussionThread = (Discussion) post;

            if (view == null) view = inflater.inflate(R.layout.listitem_discussion_thread, null);
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
            if (mainUserKey.equals(discussionThread.getPostedByKey())) {
                postDetailsView.setText("Posted by you on "+dateFormat.format(discussionThread.getPostedDateTime()));
            } else userViewModel.getUsers().observe(fragmentActivity, new Observer<HashMap<String, User>>() {
                @Override
                public void onChanged(HashMap<String, User> userMap) {
                    String name = "...";
                    if(userMap.containsKey(discussionThread.getPostedByKey())) {
                        name = userMap.get(discussionThread.getPostedByKey()).getName();
                    }
                    postDetailsView.setText("Posted by " + name + " on " + dateFormat.format(discussionThread.getPostedDateTime()));
                }
            });
            return view;
        } else if (post instanceof PYP) {
            final PYP pyp = (PYP) post;

            if (view == null) view = inflater.inflate(R.layout.listitem_pyp_thread, null);
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
            if (mainUserKey.equals(pyp.getPostedByKey())) {
                postDetailsView.setText("Posted by you on "+dateFormat.format(pyp.getPostedDateTime()));
            } else {
                userViewModel.getUsers().observe(fragmentActivity, new Observer<HashMap<String, User>>() {
                    @Override
                    public void onChanged(HashMap<String, User> userMap) {
                        String name = "...";
                        if(userMap.containsKey(pyp.getPostedByKey())) {
                            name = userMap.get(pyp.getPostedByKey()).getName();
                        }
                        postDetailsView.setText("Posted by " + name + " on " + dateFormat.format(pyp.getPostedDateTime()));
                    }
                });
            }
            return view;
        }
        return null;
    }

    public void updateData(Context context, FragmentActivity activity, List<Object> posts, String mainUserKey){
        this.context = context;
        this.fragmentActivity = activity;
        this.posts = posts;
        this.mainUserKey = mainUserKey;
        notifyDataSetChanged();
    }
}
