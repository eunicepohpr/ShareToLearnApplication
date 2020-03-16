package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.CommentNumberLiveData;
import com.cz3002.sharetolearn.viewModel.LikeNumbersLiveData;
import com.cz3002.sharetolearn.viewModel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import androidx.lifecycle.Observer;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class DiscussionAdapter extends BaseAdapter {
    private Context context;
    private FragmentActivity fragmentActivity;
    private List<Discussion> discussionList;
    private User mainUser;
    private static LayoutInflater inflater = null;
    private LikeNumbersLiveData likeNumbersViewModel;
    private CommentNumberLiveData commentNumberLiveData;
    private UserViewModel userViewModel;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);

    public DiscussionAdapter(Context context, FragmentActivity activity, List<Discussion> discussions, User mainUser) {
        this.context = context;
        this.fragmentActivity = activity;
        this.discussionList = discussions;
        this.mainUser = mainUser;
        this.likeNumbersViewModel = ViewModelProviders.of(activity).get(LikeNumbersLiveData.class);
        this.commentNumberLiveData = ViewModelProviders.of(activity).get(CommentNumberLiveData.class);
        this.userViewModel = ViewModelProviders.of(activity).get(UserViewModel.class);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return discussionList.size();
    }

    @Override
    public Object getItem(int i) {
        return discussionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Discussion discussionThread = discussionList.get(i);

        if (view == null) view = inflater.inflate(R.layout.listitem_discussion_thread, null);
        TextView topicTextView = view.findViewById(R.id.topic_title);
        topicTextView.setText(discussionThread.getTitle());
        final TextView commentNumberTextView = view.findViewById(R.id.comment_number);
        commentNumberLiveData.getCommentNumber(discussionThread).observe(fragmentActivity, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer commentNumber) {
                commentNumberTextView.setText(commentNumber.toString());
            }
        });
        final TextView likedTextView = view.findViewById(R.id.liked_number);
        likeNumbersViewModel.getLikeNumber(discussionThread).observe(fragmentActivity, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer likeNumber) {
                likedTextView.setText(likeNumber.toString());
            }
        });
        String name;
        if (discussionThread.getPostedByKey().equals(mainUser.getKey())) {
            name = "you";
        }else name = userViewModel.getUserMap().get(discussionThread.getPostedByKey()).getName();

        TextView postDetail = view.findViewById(R.id.postDetails);
        postDetail.setText("Posted by " +name+" on "+dateFormat.format(discussionThread.getPostedDateTime()));
        return view;
    }
}
