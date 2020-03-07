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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DiscussionAdapter extends BaseAdapter {
    private Context context;
    private List<Discussion> discussionList;
    private static LayoutInflater inflater = null;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);

    public DiscussionAdapter(Context context, List<Discussion> discussions) {
        this.context = context;
        this.discussionList = discussions;
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
        TextView commentNumberTextView = view.findViewById(R.id.comment_number);
        commentNumberTextView.setText(Integer.toString(discussionThread.getResponseKeys().size()));
        TextView likedTextView = view.findViewById(R.id.liked_number);
        likedTextView.setText(Integer.toString(discussionThread.getLikeKeys().size()));
        String name = discussionThread.getPostedBy().getName();
        TextView postDetail = view.findViewById(R.id.postDetails);
        postDetail.setText("Posted by " +name+" on "+dateFormat.format(discussionThread.getPostedDateTime()));
        return view;
    }
}
