package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.PYP;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.Pyp.CommentNumberPypLiveData;
import com.cz3002.sharetolearn.viewModel.Pyp.LikeNumberPypLiveData;
import com.cz3002.sharetolearn.viewModel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class PYPAdapter extends BaseAdapter {
    private Context context;
    private FragmentActivity fragmentActivity;
    private List<PYP> PYPList;
    private String mainUserKey;
    private static LayoutInflater inflater = null;
    private LikeNumberPypLiveData likeNumbersViewModel;
    private CommentNumberPypLiveData commentNumberPYPLiveData;
    private UserViewModel userViewModel;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);

    public PYPAdapter(Context context, FragmentActivity activity, List<PYP> PYPs, String mainUserKey) {
        this.context = context;
        this.fragmentActivity = activity;
        this.PYPList = PYPs;
        this.mainUserKey = mainUserKey;
        this.likeNumbersViewModel = ViewModelProviders.of(activity).get(LikeNumberPypLiveData.class);
        this.commentNumberPYPLiveData = ViewModelProviders.of(activity).get(CommentNumberPypLiveData.class);
        this.userViewModel = ViewModelProviders.of(activity).get(UserViewModel.class);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return PYPList.size();
    }

    @Override
    public Object getItem(int i) {
        return PYPList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final PYP pyp = PYPList.get(i);

        if (view == null) view = inflater.inflate(R.layout.listitem_pyp_thread, null);
        TextView topicTextView = view.findViewById(R.id.topic_title);
        topicTextView.setText(pyp.getTitle());
        final TextView commentNumberTextView = view.findViewById(R.id.comment_number);
        commentNumberPYPLiveData.getCommentNumber(pyp).observe(fragmentActivity, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer commentNumber) {
                commentNumberTextView.setText(commentNumber.toString());
            }
        });
        final TextView likedTextView = view.findViewById(R.id.liked_number);
        likeNumbersViewModel.getLikeNumber(pyp).observe(fragmentActivity, new Observer<Integer>() {
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
}
